package com.yang.springboot.d_batch;

import org.springframework.batch.item.validator.ValidatingItemProcessor;
import org.springframework.batch.item.validator.ValidationException;

/**
 * @author Yangjing
 * @explain 处理
 */
public class CsvItemProcessor extends ValidatingItemProcessor<People> {

    @Override
    public People process(People item) throws ValidationException {
        //执行super.process(item)才会调用自定义校验器
        super.process(item);
        if (item.getNation().equals("han")) {
            item.setNation("01");
        } else {
            item.setNation("02");
        }
        return item;
    }
}
