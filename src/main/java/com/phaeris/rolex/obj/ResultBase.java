package com.phaeris.rolex.obj;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

import java.util.List;

/**
 * @author wyh
 * @since 2021/1/19 17:36
 */
@Data
public class ResultBase<T> {

    public static final String SUCCEED = "0";

    public static final String NORMAL_FAILURE = "1";

    public static final String UN_AUTH = "401";

    public static final String FORBIDDEN = "403";

    private static final String SUCCEED_MSG = "ok";

    private String code;

    private String msg;

    private T result;

    private int pageNo;

    private int pageSize;

    private long total;

    private long totalPage;

    public static <T> ResultBase<T> ok(T data) {
        ResultBase<T> resultBase = new ResultBase<>();
        resultBase.setResult(data);
        resultBase.setCode(SUCCEED);
        resultBase.setMsg(SUCCEED_MSG);
        return resultBase;
    }

    public static <T> ResultBase<T> ok() {
        return ok(null);
    }

    public static <T> ResultBase<T> fail(String code, String msg) {
        ResultBase<T> resultBase = new ResultBase<>();
        resultBase.setCode(code);
        resultBase.setMsg(msg);
        return resultBase;
    }

    public static <T> ResultBase<T> fail(String msg) {
        return fail(NORMAL_FAILURE, msg);
    }

    public static <T> ResultBase<List<T>> page(Page<T> page) {
        ResultBase<List<T>> resultBase = new ResultBase<>();
        resultBase.setResult(page.getRecords());
        resultBase.setPageNo(Math.toIntExact(page.getCurrent()));
        resultBase.setPageSize(Math.toIntExact(page.getSize()));
        resultBase.setTotal(page.getTotal());
        resultBase.setTotalPage(page.getPages());
        resultBase.setCode(SUCCEED);
        resultBase.setMsg(SUCCEED_MSG);
        return resultBase;
    }
}
