
package pl.kowalska.filmek.model;

import lombok.Data;

@Data
@SuppressWarnings("unused")
public class ProductionCompany {

    private long id;
    private Object logoPath;
    private String name;
    private String originCountry;

}
