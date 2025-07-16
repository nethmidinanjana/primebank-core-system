package com.primebank.ejb.service.timer;

import com.primebank.core.entity.Account;
import jakarta.annotation.Resource;
import jakarta.ejb.*;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.logging.Logger;

@Singleton
@Startup
public class InterestCalculationTimer {

    private static final Logger logger = Logger.getLogger(InterestCalculationTimer.class.getName());

    @PersistenceContext(unitName = "primeBankPU")
    private EntityManager em;

    @Resource
    private TimerService timerService;

    public void scheduleInterestCalculation(Long accountId) {

        TimerConfig timerConfig = new TimerConfig();
        timerConfig.setInfo(accountId);
        timerConfig.setPersistent(true);

        long intervalMillis = 24 * 60 * 60 * 1000; // daily
//        long intervalMillis = 60 * 1000; // 1 minute for testing

        em.getEntityManagerFactory().getCache().evictAll();
        em.flush();

        logger.info("Scheduling interest calculation for Account ID: " + accountId);
        timerService.createIntervalTimer(intervalMillis, intervalMillis, timerConfig);
    }

    @Timeout
    public void calculateInterest(Timer timer) {
        Long accountId = (Long) timer.getInfo();

        logger.info("Triggering interest calculation for Account ID: " + accountId);

        Account account = em.find(Account.class, accountId);

        if(account != null){
            BigDecimal balance = account.getBalance();
            BigDecimal interestRate = new BigDecimal("0.01");  //1%
            BigDecimal interest = balance.multiply(interestRate);

            account.setBalance(balance.add(interest));

            em.merge(account);
            logger.info("Interest applied: " + interest + " | New Balance: " + account.getBalance());
        }else {
            logger.warning("Account not found: " + accountId);
        }
    }
}
