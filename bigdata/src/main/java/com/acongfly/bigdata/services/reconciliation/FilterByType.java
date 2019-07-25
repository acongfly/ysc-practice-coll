package com.acongfly.bigdata.services.reconciliation;

import cascading.flow.FlowProcess;
import cascading.operation.BaseOperation;
import cascading.operation.Filter;
import cascading.operation.FilterCall;
import cascading.tuple.TupleEntry;
import org.apache.commons.lang.StringUtils;

public class FilterByType extends BaseOperation implements Filter {

    private final ReconciliationPorcessService.FILTER_TYPE type;

    public FilterByType(ReconciliationPorcessService.FILTER_TYPE type) {
        this.type = type;
    }

    @Override
    public boolean isRemove(FlowProcess flowProcess, FilterCall call) {
        // get the arguments TupleEntry
        TupleEntry arguments = call.getArguments();
        String wxOrderId = arguments.getString("wx_orderId");
        String wxRefundId = arguments.getString("wx_merchantBatchNo");
        String dbOrderId = arguments.getString("orderId");
        String dbrefundId = arguments.getString("merchantBatchNo");
        if (type == ReconciliationPorcessService.FILTER_TYPE.WX_NOT_EXIST) {
            if (StringUtils.isBlank(wxOrderId) && StringUtils.isBlank(wxRefundId)) {
                return false;
            }
        } else if (type == ReconciliationPorcessService.FILTER_TYPE.DB_NOT_EXIST) {
            if (StringUtils.isBlank(dbOrderId) && StringUtils.isBlank(dbrefundId)) {
                return false;
            }
        } else {
            if (StringUtils.isBlank(wxOrderId) && StringUtils.isBlank(wxRefundId)) {
                /*nothing*/
            } else if (StringUtils.isBlank(dbOrderId) && StringUtils.isBlank(dbrefundId)) {
                /*nothing*/
            } else {
                return false;
            }
        }
        return true;
    }
}
