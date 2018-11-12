package com.hazloakki.negocio.repository;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;

import com.hazloakki.negocio.modelo.HorarioNegocioDto;
import com.hazloakki.negocio.persist.SpringJdbcDao;

public class HorariosNegocioRepositoryImpl extends SpringJdbcDao implements HorariosNegocioRepository {

	private String qryGuardarHorario = "INSERT INTO negocio_horario (id_negocio,id_dia,horario_inicial,horario_final,estatus) "
			+ "VALUES(?,?,?,?,?)";
	
	private String qrySelectByNegocioAndEstatus = "SELECT * FROM negocio_horario WHERE ID_NEGOCIO = ? AND ESTATUS = ?";

	@Override
	public List<HorarioNegocioDto> findHorariosNegocios(String idNegocio, boolean estatus) {

		return jdbcTemplate.query(qrySelectByNegocioAndEstatus, new Object[] { idNegocio, estatus },
				BeanPropertyRowMapper.newInstance(HorarioNegocioDto.class));
	}

	@Override
	public void save(String idNegocio, Integer idDia, String horarioInicial, String horarioFinal, boolean estatus) {
		 jdbcTemplate.update(qryGuardarHorario, idNegocio, idDia, horarioInicial, horarioFinal, estatus);
	}

}
