package com.common.demo;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class PaginationResponse<T> {
    public double pages;
    public double count;
    public double currentPage;
    public ArrayList<T> items;

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

    public ArrayList<T> getItems() {
        return items;
    }

    public void setItems(ArrayList<T> items) {
        this.items = items;
    }
}
