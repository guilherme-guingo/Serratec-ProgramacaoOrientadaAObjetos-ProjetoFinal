package br.com.folhapag.utils;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import br.com.folhapag.exceptions.DataInvalida;
import br.com.folhapag.exceptions.FormatoDataInvalido;

public class ValidarData {

    public static LocalDate validar(String dataTexto) throws FormatoDataInvalido, DataInvalida {
        if (dataTexto == null || dataTexto.trim().isEmpty()) {
            throw new FormatoDataInvalido("A data não pode estar em branco.");
        }

        if (!dataTexto.matches("\\d{4}-\\d{2}-\\d{2}")) {
            throw new FormatoDataInvalido("Formato inválido. Use o padrão AAAA-MM-DD (ex: 1990-05-20).");
        }

        LocalDate data;
        try {
            data = LocalDate.parse(dataTexto);
        } catch (DateTimeParseException e) {
            throw new DataInvalida("Data inexistente. Verifique se o dia e o mês estão corretos no calendário.");
        }

        return validar(data);
    }

    public static LocalDate validar(LocalDate data) throws DataInvalida {
        if (data == null) {
            throw new DataInvalida("A data não pode ser nula.");
        }

        if (data.isAfter(LocalDate.now())) {
            throw new DataInvalida("Data inválida: A data não pode ser no futuro.");
        }

        return data;
    }
}