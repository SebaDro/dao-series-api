package org.n52.series.srv;

import org.n52.io.response.OfferingOutput;
import org.n52.series.db.da.OutputAssembler;
import org.n52.series.db.dao.DbQueryFactory;
import org.springframework.stereotype.Component;

@Component
public class OfferingService extends AccessService<OfferingOutput> {

    public OfferingService(OutputAssembler<OfferingOutput> repository, DbQueryFactory queryFactory) {
        super(repository, queryFactory);
    }

}
