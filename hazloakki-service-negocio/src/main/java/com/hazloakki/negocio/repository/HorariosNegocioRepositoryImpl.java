package com.hazloakki.negocio.repository;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.hazloakki.negocio.modelo.HorarioNegocioDto;
import com.hazloakki.negocio.persist.SpringJdbcDao;

@Repository
public class HorariosNegocioRepositoryImpl extends SpringJdbcDao implements HorariosNegocioRepository {

	private String qryGuardarHorario = "INSERT INTO negocio_horario (id_negocio,id_dia,horario_inicial,horario_final,estatus) "
			+ "VALUES(?,?,?,?,?)";

	private String qrySelectByNegocioAndEstatus = "SELECT * FROM negocio_horario WHERE ID_NEGOCIO = ? AND ESTATUS = ?";
	private String qryDeleteNegocios = "DELETE FROM negocio_horario WHERE ID_NEGOCIO = ?";

	private String qrySelectNegociosAbiertos = "select * from negocio_horario a" + "where a.id_dia = ?"
			+ "and (TO_TIMESTAMP(?, 'HH24:MI:SS')::TIME >= a.horario_inicial OR "
			+ "TO_TIMESTAMP(?, 'HH24:MI:SS')::TIME <= a.horario_final)";

	@Override
	public List<HorarioNegocioDto> findHorarioNegocioByEstatus(String idNegocio, boolean estatus) {

		return jdbcTemplate.query(qrySelectByNegocioAndEstatus, new Object[] { idNegocio, estatus },
				BeanPropertyRowMapper.newInstance(HorarioNegocioDto.class));
	}

	@Override
	public void save(String idNegocio, Integer idDia, String horarioInicial, String horarioFinal, boolean estatus) {
		jdbcTemplate.update(qryGuardarHorario, idNegocio, idDia, horarioInicial, horarioFinal, estatus);
	}

	@Override
	public void delete(String idNegocio) {
		jdbcTemplate.update(qryDeleteNegocios, idNegocio);
	}

	@Override
	public List<HorarioNegocioDto> findNegociosAbiertos(String idNegocio, String horaActual, String idDia) {
		return jdbcTemplate.query(qrySelectNegociosAbiertos,
				new Object[] { idNegocio, idDia, horaActual, horaActual },
				BeanPropertyRowMapper.newInstance(HorarioNegocioDto.class));
	}

}
