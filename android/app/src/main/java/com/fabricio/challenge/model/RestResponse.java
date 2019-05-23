package com.fabricio.challenge.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * /// TODO improve this text >.<
 * Expected REST response of a pageable of given type T of object to be parsed
 * @param <T> Class in which the data response will come back
 */
public class RestResponse<T> {

    @Expose
    @SerializedName("data")
    private List<T> data;

    @Expose
    @SerializedName("offset")
    private Integer offset;

    @Expose
    @SerializedName("total")
    private Integer total;

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
