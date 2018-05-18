package org.n52.series.srv;

import org.n52.io.response.dataset.StationOutput;
import org.n52.series.db.da.OutputAssembler;
import org.n52.series.db.dao.DbQueryFactory;

public class StationService extends AccessService<StationOutput> {

    public StationService(OutputAssembler<StationOutput> repository, DbQueryFactory queryFactory) {
        super(repository, queryFactory);
    }

}
