package logic.treinamento.bean;

import java.io.File;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import junit.framework.TestCase;
import org.apache.openejb.junit.ApplicationComposer;
import org.junit.Test;
import org.junit.runner.RunWith;
import javax.ejb.EJB;
import logic.treinamento.model.Lancamento;
import logic.treinamento.dao.LancamentoDao;
import logic.treinamento.dao.TipoLancamentoEnum;
import logic.treinamento.request.AtualizarLancamentoRequisicao;
import logic.treinamento.request.LancarContasDoMesRequisicao;
import org.apache.openejb.jee.EjbJar;
import org.apache.openejb.jee.StatelessBean;
import org.junit.AfterClass;
import utilitarios.Formatadores;

@RunWith(ApplicationComposer.class)
public class GestaoContasTest extends TestCase {

    @AfterClass
    public static void after() {
        File folder = new File("C:\\banco");
        if (folder.isDirectory()) {
            File[] sun = folder.listFiles();
            for (File toDelete : sun) {
                toDelete.delete();
            }
        }
    }

    @EJB
    private InterfaceGestaoContas gestaoContaBean;

    @org.apache.openejb.testing.Module
    public EjbJar beans() {
        EjbJar ejbJar = new EjbJar();
        ejbJar.addEnterpriseBean(new StatelessBean(GestaoContas.class));
        ejbJar.addEnterpriseBean(new StatelessBean(LancamentoDao.class));
        ejbJar.addEnterpriseBean(new StatelessBean(Formatadores.class));
        return ejbJar;

    }

    /**
     * Teste responsavel por validar o metodo que verifica o campo de data
     * recebido no serviço.
     * ------------------------------------------------------------------------
     * Preparacao do cenario: -Foi informado uma data vazia.
     * ------------------------------------------------------------------------
     * Resultado Esperado: -Metodo validador retorna uma mensagem ao requisitor:
     * A data deve ser informada!
     *
     * @throws Exception
     */
    @Test
    public void testValidarDataVazia() throws Exception {
        try {
            Formatadores.validarDatasInformadas("");
        } catch (Exception ex) {
            assertEquals("A data deve ser informada!", ex.getMessage());
        }
    }

    /**
     * Teste responsavel por validar o metodo que verifica o campo de data
     * recebido no serviço.
     * ------------------------------------------------------------------------
     * Preparacao do cenario: -Foi informado uma data nula.
     * ------------------------------------------------------------------------
     * Resultado Esperado: -Metodo validador retorna uma mensagem ao requisitor:
     * A data deve ser informada!
     *
     * @throws Exception
     */
    @Test
    public void testValidarDataNula() throws Exception {
        try {
            Formatadores.validarDatasInformadas(null);
        } catch (Exception ex) {
            assertEquals("A data deve ser informada!", ex.getMessage());
        }
    }

    /**
     * Teste responsavel por validar o metodo que verifica o campo de data
     * recebido no serviço.
     * ------------------------------------------------------------------------
     * Preparacao do cenario: -Foi informado uma data em um formato nao esperado
     * pelo serviço.
     * ------------------------------------------------------------------------
     * Resultado Esperado: -Metodo validador retorna uma mensagem ao requisitor:
     * Data no formato invalido!
     *
     * @throws Exception
     */
    @Test
    public void testValidarFormatoData() throws Exception {
        try {
            Formatadores.validarDatasInformadas("31-12-2017");
        } catch (Exception ex) {
            assertEquals("Data no formato invalido!", ex.getMessage());
        }
    }

    /**
     * Teste responsavel por validar o metodo que verifica os campos de data
     * inicial e final recebidas no serviço.
     * ------------------------------------------------------------------------
     * Preparacao do cenario: -Foi informado uma data inicial superior à data
     * final.
     * ------------------------------------------------------------------------
     * Resultado Esperado: -Metodo validador retorna uma mensagem ao requisitor:
     * A data inicial nao pode ser maior que a data final!!
     *
     * @throws Exception
     */
    @Test
    public void testValidarPeriodoData() throws Exception {
        try {
            Formatadores.validarDatasInformadas("31/12/2017", "01/12/2017");
        } catch (Exception ex) {
            assertEquals("A data inicial nao pode ser maior que a data final!", ex.getMessage());
        }
    }

    /**
     * Teste responsavel por validar o metodo que verifica os campos
     * obrigatorios recebidos pelo serviço.
     * ------------------------------------------------------------------------
     * Preparacao do cenario: -Foram informados sequencialmente todos os campos
     * nulos ou vazis que sao obrigatorios para o uso de alguns serviços do
     * sistema.
     * ------------------------------------------------------------------------
     * Resultado Esperado: -Metodo validador retorna uma mensagem ao requisitor
     * de acordo com cada campo validado.
     *
     */
    @Test
    public void testValidarCamposObrigatorios() {
        Lancamento lanc = new Lancamento();
        assertEquals("E necessario informar o nome !", gestaoContaBean.validarCamposObrigatorios(lanc));

        lanc.setNome("Jair Rillo Junior");
        assertEquals("E necessario informar um valor !", gestaoContaBean.validarCamposObrigatorios(lanc));

        lanc.setNome("Jair Rillo Junior");
        lanc.setValor(BigDecimal.valueOf(1200.05D));
        assertEquals("E necessario informar um tipo de lancamento Valido !", gestaoContaBean.validarCamposObrigatorios(lanc));

        lanc.setNome("Jair Rillo Junior");
        lanc.setValor(BigDecimal.valueOf(1200.05D));
        lanc.setTipoLancamento(TipoLancamentoEnum.DEPOSITO);
        assertEquals("E necessario informar a data do lancamento !", gestaoContaBean.validarCamposObrigatorios(lanc));

        lanc.setNome("Jair Rillo Junior");
        lanc.setValor(BigDecimal.valueOf(1200.05D));
        lanc.setTipoLancamento(TipoLancamentoEnum.DEPOSITO);
        lanc.setData(new Date(new java.util.Date().getTime()));
        assertEquals("", gestaoContaBean.validarCamposObrigatorios(lanc));

    }

    /**
     * Teste responsavel por validar o serviço de lançamento de contas do mês e
     * consultar o lançamento salvo através de parte do nome do cliente.
     * ------------------------------------------------------------------------
     * Preparacao do cenario: -Foi informado todos os campos principais
     * necessarios para a correta inclusao dos dados
     * ------------------------------------------------------------------------
     * Resultado Esperado: -Sistema persistiu o lancamento de conta em bando de
     * dados e consultou corretamente os dados salvos informando apenas parte do
     * nome do cliente
     *
     * @throws Exception
     */
    @Test
    public void testLancarContasDoMes() throws Exception {

        LancarContasDoMesRequisicao lancRequisicao = new LancarContasDoMesRequisicao();
        lancRequisicao.setNome("Albert Einstein");
        lancRequisicao.setValor(new BigDecimal(1234.56));
        lancRequisicao.setData(Formatadores.formatoDataInterface.format(new java.util.Date()));
        lancRequisicao.setIdTipoLancamento(TipoLancamentoEnum.DEPOSITO.getId());
        gestaoContaBean.lancarContasDoMes(lancRequisicao);

        List<Lancamento> lancNovo = gestaoContaBean.pesquisarLancamentoPorNome("Albert");

        if (!lancNovo.isEmpty()) {
            for (Lancamento lancamentoConsultado : lancNovo) {
                assertEquals(lancRequisicao.getNome(), lancamentoConsultado.getNome());
                assertEquals(lancRequisicao.getValor().doubleValue(), lancamentoConsultado.getValor().doubleValue());
                assertEquals(lancRequisicao.getData(), Formatadores.formatoDataInterface.format(lancamentoConsultado.getData()));
                assertEquals(TipoLancamentoEnum.getByCodigo(lancRequisicao.getIdTipoLancamento()), lancamentoConsultado.getTipoLancamento());
            }
        } else {
            fail("O lancamento bancario nao foi salvo!");
        }

        gestaoContaBean.excluirLancamento(3);
    }

    /**
     * Teste responsavel por atualizar os dados de um lancamento persistido na
     * base de dados.
     * ------------------------------------------------------------------------
     * Preparacao do cenario: -Foi incluido um lancamento na base de dados.
     * -Foram informados novas informações e requisitado o serviço de
     * atualizacao dos dados no sistema
     * ------------------------------------------------------------------------
     * Resultado Esperado: -Sistema atualizou o lancamento em bando de dados de
     * acordo com a novas informações.
     *
     * @throws Exception
     */
    @Test
    public void testAtualizarDadosLancamento() throws Exception {

        LancarContasDoMesRequisicao lancRequisicao = new LancarContasDoMesRequisicao();
        lancRequisicao.setNome("Albert Einstein");
        lancRequisicao.setValor(new BigDecimal(1234.56));
        lancRequisicao.setData(Formatadores.formatoDataInterface.format(new java.util.Date()));
        lancRequisicao.setIdTipoLancamento(TipoLancamentoEnum.DEPOSITO.getId());
        gestaoContaBean.lancarContasDoMes(lancRequisicao);

        List<Lancamento> lancNovo = gestaoContaBean.pesquisarLancamentoPorNome("Albert");

        if (!lancNovo.isEmpty()) {
            for (Lancamento lancamentoConsultado : lancNovo) {
                assertEquals(lancRequisicao.getNome(), lancamentoConsultado.getNome());
                assertEquals(lancRequisicao.getValor().doubleValue(), lancamentoConsultado.getValor().doubleValue());
                assertEquals(lancRequisicao.getData(), Formatadores.formatoDataInterface.format(lancamentoConsultado.getData()));
                assertEquals(TipoLancamentoEnum.getByCodigo(lancRequisicao.getIdTipoLancamento()), lancamentoConsultado.getTipoLancamento());
            }
        } else {
            fail("O lancamento bancario nao foi salvo!");
        }

        Calendar novaData = Calendar.getInstance();
        novaData.setTime(Formatadores.formatoDataInterface.parse(lancRequisicao.getData()));
        novaData.add(Calendar.DAY_OF_MONTH, 2);

        AtualizarLancamentoRequisicao atualizarLancamentoRequisicao = new AtualizarLancamentoRequisicao();
        atualizarLancamentoRequisicao.setNomeAtualizado("Charles Darwin");
        atualizarLancamentoRequisicao.setValorAtualizado(new BigDecimal(9999.99));
        atualizarLancamentoRequisicao.setDataAtualizada(Formatadores.formatoDataInterface.format(novaData.getTime()));
        atualizarLancamentoRequisicao.setIdTipoLancamentoAtualizado(TipoLancamentoEnum.TRANSFERENCIA.getId());
        atualizarLancamentoRequisicao.setId(4);
        gestaoContaBean.atualizarLancamento(atualizarLancamentoRequisicao);

        List<Lancamento> lancamentoAtualizado = gestaoContaBean.pesquisarLancamentoPorNome("Charles");

        if (!lancamentoAtualizado.isEmpty()) {
            for (Lancamento lancAtualizado : lancamentoAtualizado) {
                assertEquals(atualizarLancamentoRequisicao.getNomeAtualizado(), lancAtualizado.getNome());
                assertEquals(atualizarLancamentoRequisicao.getValorAtualizado().doubleValue(), lancAtualizado.getValor().doubleValue());
                assertEquals(atualizarLancamentoRequisicao.getDataAtualizada(), Formatadores.formatoDataInterface.format(lancAtualizado.getData()));
                assertEquals(TipoLancamentoEnum.getByCodigo(atualizarLancamentoRequisicao.getIdTipoLancamentoAtualizado()), lancAtualizado.getTipoLancamento());
            }
        } else {
            fail("O lancamento bancario nao foi atualizado!");
        }

        gestaoContaBean.excluirLancamento(4);
    }

    /**
     * Teste responsavel por excluir os dados de um lancamento persistido na
     * base de dados.
     * ------------------------------------------------------------------------
     * Preparacao do cenario: -Foi incluido um lancamento na base de dados.
     * ------------------------------------------------------------------------
     * Resultado Esperado: -Sistema excluiu os dados do lancamento corretamente.
     *
     * @throws Exception
     */
    @Test
    public void testExcluirContasDoMes() throws Exception {

        LancarContasDoMesRequisicao lancRequisicao = new LancarContasDoMesRequisicao();
        lancRequisicao.setNome("Albert Einstein");
        lancRequisicao.setValor(new BigDecimal(1234.56));
        lancRequisicao.setData(Formatadores.formatoDataInterface.format(new java.util.Date()));
        lancRequisicao.setIdTipoLancamento(TipoLancamentoEnum.DEPOSITO.getId());
        gestaoContaBean.lancarContasDoMes(lancRequisicao);

        List<Lancamento> lancNovo = gestaoContaBean.pesquisarLancamentoPorNome("Albert");

        if (!lancNovo.isEmpty()) {
            for (Lancamento lancamentoConsultado : lancNovo) {
                assertEquals(lancRequisicao.getNome(), lancamentoConsultado.getNome());
                assertEquals(lancRequisicao.getValor().doubleValue(), lancamentoConsultado.getValor().doubleValue());
                assertEquals(lancRequisicao.getData(), Formatadores.formatoDataInterface.format(lancamentoConsultado.getData()));
                assertEquals(TipoLancamentoEnum.getByCodigo(lancRequisicao.getIdTipoLancamento()), lancamentoConsultado.getTipoLancamento());
            }
        } else {
            fail("O lancamento bancario nao foi salvo!");
        }

        gestaoContaBean.excluirLancamento(2);

        List<Lancamento> lancExcluido = gestaoContaBean.pesquisarLancamentoPorNome("Albert");

        if (!lancExcluido.isEmpty()) {
            fail("O lancamento bancario nao foi excluido!");
        }
    }

    /**
     * Teste responsavel por pesquisar os dados de um lancamento persistido na
     * base de dados atraves do tipo do lancamento.
     * ------------------------------------------------------------------------
     * Preparacao do cenario: -Foi incluido um lancamento na base de dados com o
     * tipo de lancamento SAQUE.
     * ------------------------------------------------------------------------
     * Resultado Esperado: -Sistema consultou corretamente o registro atraves do
     * tipo de lancamento informado (SAQUE)
     *
     * @throws Exception
     */
    @Test
    public void testPesquisarPorTipoDeLancamentoContasDoMes() throws Exception {

        LancarContasDoMesRequisicao lancRequisicao = new LancarContasDoMesRequisicao();
        lancRequisicao.setNome("Albert Einstein");
        lancRequisicao.setValor(new BigDecimal(1234.56));
        lancRequisicao.setData(Formatadores.formatoDataInterface.format(new java.util.Date()));
        lancRequisicao.setIdTipoLancamento(TipoLancamentoEnum.SAQUE.getId());
        gestaoContaBean.lancarContasDoMes(lancRequisicao);

        List<Lancamento> lancamentoDeSaque = gestaoContaBean.pesquisarLancamentoPorTipoDeLancamento(TipoLancamentoEnum.SAQUE.getId());

        if (!lancamentoDeSaque.isEmpty()) {
            for (Lancamento lancamentoConsultado : lancamentoDeSaque) {
                assertEquals(lancRequisicao.getNome(), lancamentoConsultado.getNome());
                assertEquals(lancRequisicao.getValor().doubleValue(), lancamentoConsultado.getValor().doubleValue());
                assertEquals(lancRequisicao.getData(), Formatadores.formatoDataInterface.format(lancamentoConsultado.getData()));
                assertEquals(TipoLancamentoEnum.getByCodigo(lancRequisicao.getIdTipoLancamento()), lancamentoConsultado.getTipoLancamento());
            }
        } else {
            fail("O lancamento bancario nao foi encontrado!");
        }

        gestaoContaBean.excluirLancamento(1);
    }

    /**
     * Teste responsavel por pesquisar os dados de lancamentos persistidos na
     * base de dados informando apenas um periodo.
     * ------------------------------------------------------------------------
     * Preparacao do cenario: -Foram inseridos na base de dados dois lancamentos
     * distintos.
     * ------------------------------------------------------------------------
     * Resultado Esperado: -Sistema consultou os lancamentos corretamente
     * atraves do serviço de consulta por periodo.
     *
     * @throws Exception
     */
    @Test
    public void testPesquisarPorPeriodoDeLancamentoContasDoMes() throws Exception {

        LancarContasDoMesRequisicao lancRequisicao = new LancarContasDoMesRequisicao();
        lancRequisicao.setNome("Albert Einstein");
        lancRequisicao.setValor(new BigDecimal(1234.56));
        lancRequisicao.setData(Formatadores.formatoDataInterface.format(new java.util.Date()));
        lancRequisicao.setIdTipoLancamento(TipoLancamentoEnum.DEPOSITO.getId());
        gestaoContaBean.lancarContasDoMes(lancRequisicao);

        Calendar novaData = Calendar.getInstance();
        novaData.setTime(Formatadores.formatoDataInterface.parse(lancRequisicao.getData()));
        novaData.add(Calendar.DAY_OF_MONTH, 5);

        LancarContasDoMesRequisicao lancDoisRequisicao = new LancarContasDoMesRequisicao();
        lancDoisRequisicao.setNome("Charles Darwin");
        lancDoisRequisicao.setValor(new BigDecimal(4242.31));
        lancDoisRequisicao.setData(Formatadores.formatoDataInterface.format(novaData.getTime()));
        lancDoisRequisicao.setIdTipoLancamento(TipoLancamentoEnum.SAQUE.getId());
        gestaoContaBean.lancarContasDoMes(lancDoisRequisicao);

        novaData.add(Calendar.DAY_OF_MONTH, 10);
        List<Lancamento> lancamentoDeSaque = gestaoContaBean.pesquisarLancamentoPorPeriodo(Formatadores.formatoDataInterface.format(new java.util.Date()), Formatadores.formatoDataInterface.format(novaData.getTime().getTime()));

        if (!lancamentoDeSaque.isEmpty()) {
            for (Lancamento lancamentoConsultado : lancamentoDeSaque) {
                if (lancamentoConsultado.getNome().equals(lancRequisicao.getNome())) {
                    assertEquals(lancRequisicao.getNome(), lancamentoConsultado.getNome());
                    assertEquals(lancRequisicao.getValor().doubleValue(), lancamentoConsultado.getValor().doubleValue());
                    assertEquals(lancRequisicao.getData(), Formatadores.formatoDataInterface.format(lancamentoConsultado.getData()));
                    assertEquals(TipoLancamentoEnum.getByCodigo(lancRequisicao.getIdTipoLancamento()), lancamentoConsultado.getTipoLancamento());
                } else if (lancamentoConsultado.getNome().equals(lancDoisRequisicao.getNome())) {
                    assertEquals(lancDoisRequisicao.getNome(), lancamentoConsultado.getNome());
                    assertEquals(lancDoisRequisicao.getValor().doubleValue(), lancamentoConsultado.getValor().doubleValue());
                    assertEquals(lancDoisRequisicao.getData(), Formatadores.formatoDataInterface.format(lancamentoConsultado.getData()));
                    assertEquals(TipoLancamentoEnum.getByCodigo(lancDoisRequisicao.getIdTipoLancamento()), lancamentoConsultado.getTipoLancamento());
                } else {
                    fail("O lancamento bancario nao foi encontrado!");
                }
            }
        } else {
            fail("O lancamento bancario nao foi encontrado!");
        }

        gestaoContaBean.excluirLancamento(5);
        gestaoContaBean.excluirLancamento(6);
    }

}
