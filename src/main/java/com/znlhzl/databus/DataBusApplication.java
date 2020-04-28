package com.znlhzl.databus;

import com.maihaoche.starter.mq.annotation.EnableMQConfiguration;
import com.xpand.starter.canal.annotation.EnableCanalClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * databus 数据采集平台
 *
 * @author dongchuang
 */
@SpringBootApplication
@EnableCanalClient
@EnableMQConfiguration
@EnableTransactionManagement
@EnableScheduling
@EnableAsync
@MapperScan("com.znlhzl.databus.mapper")
public class DataBusApplication {

	public static void main(String[] args) {
		SpringApplication.run(DataBusApplication.class, args);
	}
}