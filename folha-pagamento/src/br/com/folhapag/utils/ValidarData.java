package br.com.folhapag.utils;

import java.time.LocalDate;
import br.com.folhapag.exceptions.DataInvalida;

public class ValidarData {
	
	public static LocalDate validar(LocalDate data) throws DataInvalida {
        if (data == null) {
            throw new DataInvalida("A data não pode ser nula.");
        }

        if (data.isAfter(LocalDate.now())) {
            throw new DataInvalida("Data inválida: A data de nascimento não pode ser no futuro.");
        }
        
        return data;
	}
}


