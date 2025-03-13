package modelos.produto;

import dataFactory.ProdutoData;
import io.restassured.http.ContentType;
import model.ComponenteModel;
import model.ProdutoModel;
import model.UsuarioModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;
import static util.Util.PATH;
import static util.Util.URL;

@DisplayName("Testes de API Rest Modulo de Produto")
public class ProdutoTest {
    private static String token;
    private static UsuarioModel usuarioModel;

    private static ProdutoModel produtoModel = new ProdutoModel();

    private static ComponenteModel componenteModel = new ComponenteModel();

    @BeforeEach
    @DisplayName("Inicializa o Processo de Obtencao do Token")
    public void inicializarURIPathToken() {
        //URI contido da lojinha
        baseURI = URL;
        //Path contido da lojinha
        basePath = PATH;

        //Obter o token do usuario
        usuarioModel = new UsuarioModel("matheus.andrade", "123456");

        token = given()
                .contentType(ContentType.JSON)
                .body(usuarioModel)
                .when()
                .post("/login")
                .then()
                .statusCode(200)
                .extract()
                .path("data.token");
        //finaliza processo de obtencao do token
    }

    @Test
    @DisplayName("Validar o limite 0.00 - proibido do valor do produto")
    public void testValidarLimiteProibidoValorZeroProduto() {

        //Validar produto
        /*String validarProduto =*/
        given()
                .header("token", token)
                .contentType(ContentType.JSON)
                .body(ProdutoData.CriarProdutoModel(0.00))
                .when()
                .post("/produtos")
                .then()
                .assertThat()
                .body("error", equalTo("O valor do produto deve estar entre R$ 0,01 e R$ 7.000,00"))
                .statusCode(422);
        //.extract().path("error");
        //System.out.println(validarProduto);
//                                                .extract()
//                                        .path("error");

        // Assertions.assertEquals(validarProduto,"O valor do produto deve estar entre R$ 0,01 e R$ 7.000,00");

    }

    @Test
    @DisplayName("Validar o limite 7000.00 - proibido do valor do produto")
    public void testValidarLimiteProibidoValorSeteMilProduto() {

        //Validar produto
        /*String validarProduto =*/
        given()
                .header("token", token)
                .contentType(ContentType.JSON)
                .body(ProdutoData.CriarProdutoModel(7000.01))
        .when()
                .post("/produtos")
        .then()
                .assertThat()
                .body("error", equalTo("O valor do produto deve estar entre R$ 0,01 e R$ 7.000,00"))
                .statusCode(422);
//                                                .extract()
//                                        .path("error");

        // Assertions.assertEquals(validarProduto,"O valor do produto deve estar entre R$ 0,01 e R$ 7.000,00");

    }

    @Test
    @DisplayName("Adicionar um novo produto")
    public void testeValidarInsercaodoProduto() {

        given()
                .header("token", token)
                .contentType(ContentType.JSON)
                .body(ProdutoData.CriarProdutoModel(150.00))
                .when()
                .post("/produtos")
                .then()
                .assertThat()
                .body("message", equalTo("Produto adicionado com sucesso"))
                .statusCode(201);
    }

    @Test
    @DisplayName("Buscar os produtos do usuário")
    public void testeValidarBuscadosProdutos() {

        List<String> f = given()
                .header("token", token)
                .contentType(ContentType.JSON)
                .when()
                .get("/produtos")
                .then()
                .assertThat()
                .body("message", equalTo("Listagem de produtos realizada com sucesso"))
                .statusCode(200)
                .extract()
                .path("data");

        System.out.println(f.stream().toList());

    }

    @Test
    @DisplayName("Buscar os produtos do usuário-Nome do Produto")
    public void testeValidarBuscardoProdutoPorNome() {

        produtoModel.setProdutoNome("Balinha Viva Lagarto Folia 2024");

        List<String> f = given()
                                .header("token", token)
                                .contentType(ContentType.JSON)
                        .when()
                                .get("/produtos?produtoNome="+produtoModel.getProdutoNome())
                        .then()
                                .assertThat()
                                .body("message", equalTo("Listagem de produtos realizada com sucesso"))
                                .statusCode(200)
                                .extract()
                                .path("data");

        System.out.println(f.stream().toList());

    }

    @Test
    @DisplayName("Buscar os produtos do usuário-Nome do Produto e Cores")
    public void testeValidarBuscadosProdutoPorNomeeCores() {

        produtoModel.setProdutoNome("Balinha Viva Lagarto Folia 2024");
        produtoModel.setProdutoCores(Arrays.asList("Amarelo,Rosa"));

        List<String> f = given()
                            .header("token", token)
                            .contentType(ContentType.JSON)
                        .when()
                            .get("/produtos?produtoNome="+produtoModel.getProdutoNome()+"&produtoCor="+produtoModel.getProdutoCores())
                        .then()
                            .assertThat()
                            .body("message", equalTo("Listagem de produtos realizada com sucesso"))
                            .statusCode(200)
                            .extract()
                            .path("data");

        System.out.println(f.stream().toList());

    }

    @Test
    @DisplayName("Buscar um dos produtos do usuário - Por ID")
    public void testeValidarBuscadorProdutoPorID() {

        produtoModel.setProdutoId(933182L);

        LinkedHashMap<String,String> f = given()
                            .header("token", token)
                            .contentType(ContentType.JSON)
                        .when()
                            .get("/produtos/"+produtoModel.getProdutoId())
                        .then()
                            .assertThat()
                            .body("message", equalTo("Detalhando dados do produto"))
                            .statusCode(200)
                            .extract()
                            .path("data");

        System.out.println(f.entrySet());

    }

    @Test
    @DisplayName("Alterar informações de um produto")
    public void testeValidarAlteracaodeProduto() {

        produtoModel.setProdutoId(934341L);

        LinkedHashMap<String,String> f = given()
                                            .header("token", token)
                                            .contentType(ContentType.JSON)
                                            .body(ProdutoData.CriarProdutoModel(156.85))
                                        .when()
                                            .put("/produtos/"+produtoModel.getProdutoId())
                                        .then()
                                                .assertThat()
                                        .body("message", equalTo("Produto alterado com sucesso"))
                                                .statusCode(200)
                                                .extract()
                                                .path("data");
        System.out.println(f.entrySet());

    }

    @Test
    @DisplayName("Remover um produto")
    public void testeValidarRemoverProduto() {
        // identificacao do id
        produtoModel.setProdutoId(934340L);

        given()
                                            .header("token", token)
                                            .contentType(ContentType.JSON)
                                        .when()
                                            .delete("/produtos/"+produtoModel.getProdutoId())
                                            .then()
                                            .assertThat()
                //.body("message", equalTo("Produto alterado com sucesso"))
                                            .statusCode(204);


    }


}
