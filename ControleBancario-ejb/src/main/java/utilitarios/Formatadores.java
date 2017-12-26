package utilitarios;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class Formatadores {

    public static final DateFormat formatoDataBanco = new SimpleDateFormat("yyyy-MM-dd");
    public static final DateFormat formatoDataInterface = new SimpleDateFormat("dd/MM/yyyy");
    public static final SimpleDateFormat formatoExibicaoDataInterface = new SimpleDateFormat("dd/MM/yyyy");
    
    public static List<Date> validarDatasInformadas(String... datas) throws Exception {
        List<Date> listaDatas = new ArrayList<>();

        if (datas == null) {
            throw new Exception("A data deve ser informada!");
        }

        for (String data : datas) {

            if (data.isEmpty()) {
                throw new Exception("A data deve ser informada!");
            }

            java.sql.Date dataSQL;
            try {
                dataSQL = new java.sql.Date(formatoDataInterface.parse(data).getTime());
            } catch (ParseException ex) {
                throw new Exception("Data no formato invalido!");
            }

            if (!formatoDataInterface.format(dataSQL).equals(data)) {
                throw new Exception("A data informada é invalida!");
            }

            listaDatas.add(dataSQL);
        }

        if (listaDatas.size() == 2 && listaDatas.get(0).after(listaDatas.get(1))) {
            throw new Exception("A data inicial nao pode ser maior que a data final!");
        }

        return listaDatas;

    }
    
    public static String formatarDataGui(Date data){
         return formatoExibicaoDataInterface.format(data);
    }
}
