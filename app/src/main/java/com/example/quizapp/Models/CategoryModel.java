package com.example.quizapp.Models;

public class CategoryModel {

   private String categoryName,categoryImage,key;
   int setNum;

   public CategoryModel(String categoryName, String categoryImage, String key, int setNum) {
       this.categoryName = categoryName;
       this.categoryImage = categoryImage;
       this.key = key;
       this.setNum = setNum;
   }

   public CategoryModel() {
   }



   public String getCategoryName() {
       return categoryName;
   }

   public String getCategoryImage() {
       return categoryImage;
   }

   public void setCategoryName(String categoryName) {
       this.categoryName = categoryName;
   }

   public void setSetNum(int setNum) {
       this.setNum = setNum;
   }

   public void setCategoryImage(String categoryImage) {
       this.categoryImage = categoryImage;
   }

    public int getSetNum() {
       return setNum;
    }

    public String getKey() {
       return key;
    }
}