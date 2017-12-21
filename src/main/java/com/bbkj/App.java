package com.bbkj;

import com.bbkj.threadpool.RequestProcessorThreadPool;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;;
import java.sql.SQLException;
import java.util.concurrent.*;

@SpringBootApplication
@ServletComponentScan
public class App {
    public static void main(String[] args) throws SQLException, InterruptedException, ExecutionException {
        System.out.println(RequestProcessorThreadPool.getInstance());
    }
}
