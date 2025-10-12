package net.lelyak.edu.dao.impl;

import net.lelyak.edu.dao.BaseDAO;
import net.lelyak.edu.entity.AspectModel;

import java.util.Arrays;

public class AspectCounterDAO extends BaseDAO<AspectModel> {

    private static final String ASPECT_COUNTER_TABLE_NAME = "t_aspectcounter";
    private static final String[] aspect_counter_fields = {"name", "target", "aspectCount"};

    public AspectCounterDAO() {
        super(AspectModel.class, ASPECT_COUNTER_TABLE_NAME, Arrays.asList(aspect_counter_fields));
    }
}
