package model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Data
@ToString
@EqualsAndHashCode
public class ProdutoModel {

    private Long produtoId;
    private String produtoNome;
    private double produtoValor;
    private List<String> produtoCores;
    private String produtoUrlMock;
    private List<ComponenteModel> componentes;

}
