package com.example.classManagementAppServer.controller.mvc;

import java.security.*;
import java.util.*;

public class ColorSelector {

    public static List<String> colors (int colorsNum) {

        SecureRandom secureRandom = new SecureRandom();

        List<String> colors = new ArrayList<>();

        for (int i = 0; i < colorsNum; i++) {

            int firstColorNum = secureRandom.nextInt(180,256);
            int secondColorNum = secureRandom.nextInt(180,256);
            int thirdColorNum = secureRandom.nextInt(180,256);
//            double opacity = secureRandom.nextDouble(0.2, 1.0);

            String color = "rgb("+firstColorNum+","+secondColorNum+","+thirdColorNum+")";
            colors.add(color);
        }

        return colors;
    }
}
