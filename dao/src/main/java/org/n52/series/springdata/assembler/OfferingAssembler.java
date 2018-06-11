
package org.n52.series.springdata.assembler;

import org.n52.io.response.OfferingOutput;
import org.n52.series.db.beans.OfferingEntity;
import org.n52.series.springdata.OfferingDataRepository;

public class OfferingAssembler extends ParameterOutputAssembler<OfferingEntity, OfferingOutput> {

    public OfferingAssembler(OfferingDataRepository repository) {
        super(repository);
    }

}