package ru.skypro.lessons.courseworkspring.service;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.PathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.skypro.lessons.courseworkspring.DTO.*;
import ru.skypro.lessons.courseworkspring.exception.LotIdException;
import ru.skypro.lessons.courseworkspring.exception.LotWrongStatusException;
import ru.skypro.lessons.courseworkspring.model.Bid;
import ru.skypro.lessons.courseworkspring.model.Lot;
import ru.skypro.lessons.courseworkspring.repository.BidRepository;
import ru.skypro.lessons.courseworkspring.repository.LotRepository;
import ru.skypro.lessons.courseworkspring.repository.PagingAndSortingRepository;


import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LotServiceImpl implements LotService {

    private final LotRepository lotRepository;
    Logger logger = LoggerFactory.getLogger(LotServiceImpl.class);
    private final PagingAndSortingRepository pagingAndSortingRepository;
    private final BidRepository bidRepository;

    public LotServiceImpl(LotRepository lotRepository, PagingAndSortingRepository pagingAndSortingRepository, BidRepository bidRepository) {
        this.lotRepository = lotRepository;
        this.pagingAndSortingRepository = pagingAndSortingRepository;
        this.bidRepository = bidRepository;
    }
    public void createLot(CreatingLot creatingLot) {
        logger.info("Was invoked method for creating new lot {}", creatingLot);
        if (creatingLot.getBidPrice() != 0 & creatingLot.getStartPrice() != 0) {
            lotRepository.save(creatingLot.toLot());
            logger.debug("New lot was created");
        } else {
            LotWrongStatusException e = new LotWrongStatusException("Введены некорректные данные");
            logger.error("Received the invalid information", e);
        }
    }

    @Override
    public List<LotDTO> getLotsByStatus(Integer page, String status) {
        logger.info("Was invoked method for getting lots with status {} on {} page with size=10", status, page);
        boolean statusValid = status.matches("CREATED|STARTED|STOPPED");
        if (!statusValid) {
            LotWrongStatusException e = new LotWrongStatusException("Введен некорректный статус");
            logger.error("Received the invalid status {}", status, e);
        }
        Page<Lot> lotPage = pagingAndSortingRepository.findAllByStatus(PageRequest.of(page, 10), status);
        List<LotDTO> lotDTOList = lotPage.stream().map(LotDTO::fromLot).collect(Collectors.toList());
        logger.debug("Received lots {} with status {} on page {}", lotDTOList, status, page);
        return lotDTOList;
    }

    @Override
    public ResponseEntity<Resource> exportLotInFile() throws IOException {
        logger.info("Was invoked method for getting lots in CRV");
        String [] HEADERS = {"id","title","status","lastBidder","currentPrice"};
        String fileName = "lots.crv";
        FileWriter out = new FileWriter(fileName);
        try (final CSVPrinter printer = new CSVPrinter(out, CSVFormat.DEFAULT.withHeader(HEADERS))) {
            List<LotCRV> lotCRVList = lotRepository.findAll().stream().map(LotCRV :: fromLot).toList();
            lotCRVList.forEach(lot -> {
                try {
                    printer.printRecord(lot);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
        Resource resource = new PathResource(fileName);
        logger.debug("Received the file CRV");
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(resource);

    }

    @Override
    public void stopBargaining(Integer id) {
        logger.info("Was invoked method to stop bargaining for lot with id={}", id);
        LotDTO lotDTO = LotDTO.fromLot(lotRepository.findById(id).orElseThrow(() -> {
            LotIdException e = new LotIdException("Введен некорректный id");
            logger.error("Received the invalid id {}", id, e);
            return e;
        }));
        lotDTO.setStatus("STOPPED");
        lotRepository.save(lotDTO.toLot());
        logger.debug("Lot # {} was stopped", id);
    }

    @Override
    public void addBid(Integer id, String bidderName) {

        logger.info("Was invoked method for doing bid");
        Lot lot = lotRepository.findById(id).orElseThrow(() -> {
            LotIdException e = new LotIdException("Введен некорректный id");
            logger.error("Received the invalid id {}", id, e);
            return e;
        });
        if (!lot.getStatus().equals("STARTED")) {
            LotWrongStatusException e = new LotWrongStatusException("лот в неверном статусе");
            logger.error("The lot has wrong status", e);
        } else {
            Bid bid = new Bid(bidderName);
            List<Bid> bidList = lot.getBidList();
            bidList.add(bid);
            lot.setBidList(bidList);
            lotRepository.save(lot);
        }
        logger.debug("Bid was added");
    }

    @Override
    public void startBargaining(Integer id) {
        logger.info("Was invoked method to start bargaining for lot with id={}", id);
        LotDTO lotDTO = LotDTO.fromLot(lotRepository.findById(id).orElseThrow(() -> {
            LotIdException e = new LotIdException("Введен некорректный id");
            logger.error("Received the invalid id {}", id, e);
            return e;
        }));
        lotDTO.setStatus("STARTED");
        lotRepository.save(lotDTO.toLot());
        logger.debug("Lot # {} was started", id);
    }

    @Override
    public FullLotDTO getFullLot(Integer id) {
        logger.info("Wa invoked method for getting full information about lot # {}", id);
        FullLotDTO fullLotDTO = FullLotDTO.fromLot(lotRepository.findById(id).orElseThrow(() -> {
            LotIdException e = new LotIdException("Введен некорректный id");
            logger.error("Received the invalid id {}", id, e);
            return e;
        }));
        logger.debug("Received full information about lot # {}", id);
        return fullLotDTO;
    }

    @Override
    public BidDTO getTheFirstBid(Integer id) {
        logger.info("Was invoked method fot getting the first bid on lot # {}", id);
        BidDTO bidDTO = BidDTO.formBid(bidRepository.findFirstBid(id).orElseThrow(() -> {
            LotIdException e = new LotIdException("Введен некорректный id");
            logger.error("Received the invalid id {}", id, e);
            return e;
        }));
        logger.debug("Received the first bid on the lot # {}", id);
        return bidDTO;
    }

    @Override
    public FrequentBidInterface getTheFrequentBid(Integer id) {
        logger.info("Was invoked method fot getting the first bid on lot # {}", id);
//        BidDTO bidDTO = BidDTO.formBid(bidRepository.findFrequentBid(id).orElseThrow(() -> {
//            LotIdException e = new LotIdException("Введен некорректный id");
//            logger.error("Received the invalid id {}", id, e);
//            return e;
//        }));

//        BidDTO bidDTO = BidDTO.formCreatingBid(bidRepository.findFrequentCreatingBid(id).orElseThrow(() -> {
//            LotIdException e = new LotIdException("Введен некорректный id");
//            logger.error("Received the invalid id {}", id, e);
//            return e;
//        }));
        FrequentBidInterface frequentBidInterface = bidRepository.
                findFrequentBidInterface(id).orElseThrow(() -> {
            LotIdException e = new LotIdException("Введен некорректный id");
            logger.error("Received the invalid id {}", id, e);
            return e;
        });
        logger.debug("Received the frequent bid {} on the lot # {}", frequentBidInterface, id);
        return frequentBidInterface;
    }



    @Override
    public BidDTO getTheFrequentBid1(Integer id) {
        logger.info("Was invoked method fot getting the first bid on lot # {}", id);

//        BidDTO bidDTO = BidDTO.formCreatingBid(bidRepository.findFrequentCreatingBid(id).orElseThrow(() -> {
//            LotIdException e = new LotIdException("Введен некорректный id");
//            logger.error("Received the invalid id {}", id, e);
//            return e;
//        }));

        BidDTO bidDTO = BidDTO.fromFrequentBid(bidRepository.findFrequentBid(id).orElseThrow(() -> {
            LotIdException e = new LotIdException("Введен некорректный id");
            logger.error("Received the invalid id {}", id, e);
            return e;
        }));

        logger.debug("Received the frequent bid {} on the lot # {}", bidDTO, id);
        return bidDTO;
    }

    @Override
    public BidDTO getTheFrequentBid2(Integer id) {
        logger.info("Was invoked method fot getting the first bid on lot # {}", id);

        BidDTO bidDTO = BidDTO.fromFrequentBid(bidRepository.findFrequentBid2(id).get(0));

        logger.debug("Received the frequent bid {} on the lot # {}", bidDTO, id);
        return bidDTO;
    }
}
