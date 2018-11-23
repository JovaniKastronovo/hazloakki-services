package com.hazloakki.negocio.repository;

import java.util.List;

import com.hazloakki.negocio.modelo.HorarioNegocioDto;

public interface HorariosNegocioRepository {

	void save(String idNegocio, Integer idDia, String horarioInicial, String horarioFinal, boolean estatus);

	List<HorarioNegocioDto> findHorarioNegocioByEstatus(String idNegocio, boolean estatus);

	void delete(String idNegocio);

	HorarioNegocioDto findNegocioAbierto(String idNegocio, String horaActual, Integer idDia);
}
