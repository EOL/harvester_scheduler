package org.bibalex.eol.scheduler.resource;

import javax.persistence.AttributeConverter;
import javax.persistence.Convert;

import static org.bibalex.eol.scheduler.resource.Resource.HarvestFrequency.*;

@Convert
public class HarvestFreqConverter implements AttributeConverter<Resource.HarvestFrequency, String> {

    @Override
    public String convertToDatabaseColumn(Resource.HarvestFrequency harvestFrequency) {
        switch (harvestFrequency) {
            case once:
                return "0";
            case weekly:
                return "7";
            case monthly:
                return "30";
            case bimonthly:
                return "60";
            case quarterly:
                return "90";
            default:
                throw new IllegalArgumentException("Unknown " + harvestFrequency);
        }
    }

    @Override
    public Resource.HarvestFrequency convertToEntityAttribute(String dbData) {
        switch (dbData) {
            case "0":
                return once;
            case "7":
                return weekly;
            case "30":
                return monthly;
            case "60":
                return bimonthly;
            case "90":
                return quarterly;
            default:
                throw new IllegalArgumentException("Unknown " + dbData);
        }
    }

}