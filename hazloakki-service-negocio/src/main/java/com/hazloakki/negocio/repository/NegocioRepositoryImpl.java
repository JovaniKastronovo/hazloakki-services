package com.hazloakki.negocio.repository;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.hazloakki.negocio.modelo.NegocioDto;
import com.hazloakki.negocio.persist.SpringJdbcDao;

@Repository
public class NegocioRepositoryImpl extends SpringJdbcDao implements NegocioRepository {

	private String qryInsert = "INSERT INTO negocio (id_negocio,nombre,id_categoria,email,descripcion,telefono,"
			+ " domicilio,latitud,longitud,estatus,codigo_postal,delegacion,colonia,calle,"
			+ " numero_exterior,horario,responsable,id_cuenta,id_accion,sitio_web) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	private String qrySelectNegocio = "select * from negocio where id_negocio = ?";

	private String qrySelecNegociosByCuentaAndEstatus = "SELECT * FROM negocio WHERE id_cuenta = ? and estatus = ?";

	private String qryUpdateNegocio = "UPDATE negocio set nombre = ?, id_categoria = ?, email = ?, descripcion = ?,"
			+ " telefono = ?, domicilio = ?, latitud = ?, longitud = ?,  estatus = ?, codigo_postal = ?,"
			+ "  delegacion = ?, colonia = ?, calle = ? , numero_exterior = ?,  horario = ? , responsable = ? WHERE ID_NEGOCIO = ?";

	private String qryDeleteNegocio = "DELETE FROM negocio WHERE ID_NEGOCIO = ?";
	private String qrySelectNegociosByAccionAndEstatus = "SELECT * FROM negocio WHERE ID_ACCION=? AND ESTATUS = ?";
	private String qryAllNegocios = "SELECT * FROM negocio WHERE ESTATUS = ?";
	
	private String qryNegociosCercanos="select a.*,b.distancia from " + 
			"	(SELECT * FROM negocio a) as a, " + 
			"	(SELECT b.id_negocio,(6371 * acos( cos( radians(?) ) * " + 
			"		cos( radians( latitud ) ) * " + 
			"		cos( radians( longitud ) - " + 
			"		radians(?) ) + " + 
			"		sin( radians(?) ) * " + 
			"		sin( radians( latitud ) ))) / 10 as distancia " + 
			"	from negocio b) as b "+ 
			"where a.id_negocio = b.id_negocio " + 
			" and a.ESTATUS = ? "+  
			"and distancia < ? " + 
			"order by b.distancia asc ";
	
	@Override
	public String guardar(NegocioDto negocioDto) {

		negocioDto.setIdNegocio(generadorLlaves());

		jdbcTemplate.update(qryInsert, negocioDto.getIdNegocio(), negocioDto.getNombre(), negocioDto.getIdCategoria(),
				negocioDto.getEmail(), negocioDto.getDescripcion(), negocioDto.getTelefono(), negocioDto.getDomicilio(),
				negocioDto.getLatitud(), negocioDto.getLongitud(), negocioDto.isEstatus(), negocioDto.getCodigoPostal(),
				negocioDto.getDelegacion(), negocioDto.getColonia(), negocioDto.getCalle(),
				negocioDto.getNumeroExterior(), negocioDto.getHorario(), negocioDto.getResponsable(),
				negocioDto.getIdCuenta(), negocioDto.getIdAccion(), negocioDto.getSitioWeb());

		return negocioDto.getIdNegocio();
	}

	@Override
	public NegocioDto findById(String idNegocio) {

		try {
			return jdbcTemplate.queryForObject(qrySelectNegocio, new Object[] { idNegocio },
					BeanPropertyRowMapper.newInstance(NegocioDto.class));
		} catch (EmptyResultDataAccessException e) {
			return null;
		}

	}

	@Override
	public List<NegocioDto> findByIdCuentaAndEstatus(String idCuenta, boolean estatus) {
		return jdbcTemplate.query(qrySelecNegociosByCuentaAndEstatus, new Object[] { idCuenta, estatus },
				BeanPropertyRowMapper.newInstance(NegocioDto.class));
	}

	@Override
	public void actualizarByIdNegocio(String idNegocio, NegocioDto negocioDto) {
		jdbcTemplate.update(qryUpdateNegocio, negocioDto.getNombre(), negocioDto.getIdCategoria(),
				negocioDto.getEmail(), negocioDto.getDescripcion(), negocioDto.getTelefono(), negocioDto.getDomicilio(),
				negocioDto.getLatitud(), negocioDto.getLongitud(), negocioDto.isEstatus(), negocioDto.getCodigoPostal(),
				negocioDto.getDelegacion(), negocioDto.getColonia(), negocioDto.getCalle(),
				negocioDto.getNumeroExterior(), negocioDto.getHorario(), negocioDto.getResponsable(),
				negocioDto.getIdCuenta(), negocioDto.getIdNegocio());
	}

	@Override
	public void eliminarByIdNegocio(String idNegocio) {
		jdbcTemplate.update(qryDeleteNegocio, idNegocio);
	}

	@Override
	public List<NegocioDto> obtenerNegociosByIdAccionAndEstatus(Integer idAccion, boolean estatus) {

		return jdbcTemplate.query(qrySelectNegociosByAccionAndEstatus, new Object[] { idAccion, estatus },
				BeanPropertyRowMapper.newInstance(NegocioDto.class));
	}

	@Override
	public List<NegocioDto> findAllNegociosByEstatus(boolean estatus) {
		return jdbcTemplate.query(qryAllNegocios, new Object[] { estatus },
				BeanPropertyRowMapper.newInstance(NegocioDto.class));
	}

	@Override
	public List<NegocioDto> findAllNegociosByNearbyAndEstatusAndHorario(double latitudActual, double longitudActual,
			 double radio, boolean estatus) {
		
		 return jdbcTemplate.query(qryNegociosCercanos, new Object[] { latitudActual,longitudActual,latitudActual,estatus,radio},
				BeanPropertyRowMapper.newInstance(NegocioDto.class));
	}

}
