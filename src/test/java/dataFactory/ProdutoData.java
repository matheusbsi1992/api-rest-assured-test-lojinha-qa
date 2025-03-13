package dataFactory;

import model.ComponenteModel;
import model.ProdutoModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProdutoData {

    public static ProdutoModel CriarProdutoModel (double valor){

        List<ComponenteModel> componentes=new ArrayList<>();

        ProdutoModel produtoModel = new ProdutoModel();
        ComponenteModel componenteModel = new ComponenteModel();

        produtoModel.setProdutoNome("Balinha Viva Lagarto Folia 2024");
        produtoModel.setProdutoValor(valor);
        produtoModel.setProdutoCores(Arrays.asList("Amarelo, Rosa"));

        //indicacao de  vazio, ainda
        produtoModel.setProdutoUrlMock("");

        componenteModel.setComponenteNome("Cubinhos");
        componenteModel.setComponenteQuantidade(1000);
        componentes.add(componenteModel);

        componenteModel = new ComponenteModel();
        componenteModel.setComponenteNome("Enrolada");
        componenteModel.setComponenteQuantidade(150);
        componentes.add(componenteModel);

        produtoModel.setComponentes(componentes);

        return produtoModel;
    }
}
