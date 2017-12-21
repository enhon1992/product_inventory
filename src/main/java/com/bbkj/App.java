package com.bbkj;

import com.bbkj.threadpool.InventoryRequestProcessorThreadPool;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;;
import java.sql.SQLException;
import java.util.concurrent.*;

@SpringBootApplication
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class,args);
    }
}
