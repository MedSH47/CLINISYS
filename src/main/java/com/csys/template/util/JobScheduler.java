/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.csys.template.util;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


@Component
@EnableScheduling
@Service
public class JobScheduler {

    @SuppressWarnings("unused")
    private final Logger log = LoggerFactory.getLogger(JobScheduler.class);

    @Autowired
    public JdbcTemplate jdbcTemplate;
    @Autowired
    CronErrorRepository cronErrorRepository;



}
