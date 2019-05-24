package com.fabricio.challenge.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Each REST response should be return as shown in this model, where T is the object
 * type that will be returned in the data parameters of the result.
 * Note that not every query may result in a pageable result, so be sure to check
 *       if the offset and total parameters are set.
 * @param <T> Data type of the API response
 * @author Fabricio Godoi
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
