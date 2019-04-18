package ru.rencredit;

import io.qameta.allure.Step;

//Источник https://softwareautomata.wordpress.com/2017/07/09/allure-reports-in-selenium/

public final class LogUtil {
        private LogUtil() {
        }

        @Step("{0}")
        public static void log(final String message){
            //intentionally empty
        }
}
