package com.company.graphics;

 public class Color {

     String colorName;
     public int r;
     public int g;
     public int b;
     public int rgb;

     public Color(String colorName, int r, int g, int b){
         this.colorName = colorName;
         this.r = r;
         this.g = g;
         this.b = b;
         this.rgb = r<< 8;
         this.rgb =  this.rgb + g << 8;
         this.rgb =  this.rgb + b;
     }

 }
