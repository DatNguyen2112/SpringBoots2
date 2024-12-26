package com.common.demo;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class PaginationResponse {
    public double pages;
    public double count;
    public double currentPage;
    public ArrayList<Object> items;

    public double getPages() {
        return pages;
    }

    public void setPages(double pages) {
        this.pages = pages;
    }

    public double getCount() {
        return count;
    }

    public void setCount(double count) {
        this.count = count;
    }

    public double getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(double currentPage) {
        this.currentPage = currentPage;
    }

    public ArrayList<Object> getItems() {
        return items;
    }

    public void setItems(ArrayList<Object> items) {
        this.items = items;
    }
}
