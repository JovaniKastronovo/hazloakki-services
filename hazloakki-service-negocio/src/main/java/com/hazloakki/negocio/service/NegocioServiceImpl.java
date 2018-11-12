package com.hazloakki.negocio.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hazloakki.negocio.api.NegocioException;
import com.hazloakki.negocio.modelo.MetodoPagoDto;
import com.hazloakki.negocio.modelo.NegocioDto;
import com.hazloakki.negocio.modelo.ServiciosDto;
import com.hazloakki.negocio.modelo.TipoTarjetaDto;
import com.hazloakki.negocio.repository.NegocioMetodoPagoRepository;
import com.hazloakki.negocio.repository.NegocioRepository;
import com.hazloakki.negocio.repository.NegocioTarjetasPagoRepository;
import com.hazloakki.negocio.repository.ServiciosNegocioRepository;
import com.hazloakki.negocio.service.remotos.OfertaDto;
import com.hazloakki.negocio.service.remotos.OfertasNegociosApiClient;

/**
 * @author Jovani Arzate 2018-07-01 HazloAkki para Empresas v.1
 *
 */
@Service
public class NegocioServiceImpl implements NegocioService {

	@Autowired
	private NegocioRepository negocioRepository;
	@Autowired
	private OfertasNegociosApiClient ofertasNegociosApiClient;
	@Autowired
	private ServiciosNegocioRepository negocioServiciosRepository;
	@Autowired
	private NegocioMetodoPagoRepository negocioMetodoPagoRepository;
	@Autowired
	private NegocioTarjetasPagoRepository negocioTarjetasPagoRepository;

	@Transactional
	@Override
	public NegocioDto guardarNegocio(NegocioDto negocioDto) {

		String idNegocio = negocioRepository.guardar(negocioDto);
		try {
			/*
			 * Servicios por negocio
			 */
			for (ServiciosDto serviciosDto : negocioDto.getServiciosList()) {
				negocioServiciosRepository.guardar(idNegocio, serviciosDto.getId());
			}
			/*
			 * Metodos de pago por negocio
			 */
			for (MetodoPagoDto metodoPago : negocioDto.getMetodoPagoList()) {
				negocioMetodoPagoRepository.guardar(idNegocio, metodoPago.getId());
			}
			/*
			 * Tipos de tarjeta por negocio
			 */
			for (TipoTarjetaDto tipoTarjetaDto : negocioDto.getTipoTarjetaList()) {
				negocioTarjetasPagoRepository.guardar(idNegocio, tipoTarjetaDto.getId());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return obtenerNegocio(idNegocio);
	}

	@Override
	public NegocioDto obtenerNegocio(String idNegocio) {

		NegocioDto negocioDto = negocioRepository.findById(idNegocio);

		if (negocioDto == null) {
			throw new NegocioException("No se encontro el negocio : " + idNegocio, idNegocio);
		}

		List<ServiciosDto> dataServiciosNegocio = negocioServiciosRepository.findServicios(idNegocio);
		List<MetodoPagoDto> dataMetodosPagoNegocio = negocioMetodoPagoRepository.consultar(idNegocio);
		List<TipoTarjetaDto> dataTipoTarjetaNEgocio = negocioTarjetasPagoRepository.findByIdNegocio(idNegocio);

		negocioDto.setServiciosList(dataServiciosNegocio);
		negocioDto.setMetodoPagoList(dataMetodosPagoNegocio);
		negocioDto.setTipoTarjetaList(dataTipoTarjetaNEgocio);

		return negocioDto;
	}

	@Transactional
	@Override
	public NegocioDto modificaNegocio(String idNegocio, NegocioDto negocio) {

		NegocioDto negocioDto = negocioRepository.findById(idNegocio);

		if (negocioDto == null) {
			throw new NegocioException("El negocio seleccionado no esta registrado : " + idNegocio, idNegocio);
		}
		negocioRepository.actualizarByIdNegocio(idNegocio, negocio);

		/*
		 * Servicios por negocio
		 */
		for (ServiciosDto serviciosDto : negocio.getServiciosList()) {
			negocioServiciosRepository.guardar(idNegocio, serviciosDto.getId());
		}
		/*
		 * Metodos de pago por negocio
		 */
		for (MetodoPagoDto metodoPago : negocio.getMetodoPagoList()) {
			negocioMetodoPagoRepository.guardar(idNegocio, metodoPago.getId());
		}
		/*
		 * Tipos de tarjeta por negocio
		 */
		for (TipoTarjetaDto tipoTarjetaDto : negocio.getTipoTarjetaList()) {
			negocioTarjetasPagoRepository.guardar(idNegocio, tipoTarjetaDto.getId());
		}

		return obtenerNegocio(idNegocio);
	}

	@Transactional
	@Override
	public void borrarNegocio(String idNegocio) {

		NegocioDto negocioDto = negocioRepository.findById(idNegocio);

		if (negocioDto == null) {
			throw new NegocioException("El negocio seleccionado no esta registrado : " + idNegocio, idNegocio);
		}

		negocioServiciosRepository.eliminar(idNegocio);
		negocioMetodoPagoRepository.eliminar(idNegocio);
		negocioTarjetasPagoRepository.eliminar(idNegocio);

		negocioRepository.eliminarByIdNegocio(idNegocio);

	}

	@Override
	public List<NegocioDto> obtenerAllNegociosByCuenta(String idCuenta) {

		return negocioRepository.findByIdCuentaAndEstatus(idCuenta, Boolean.TRUE);
	}

	@Override
	public List<OfertaDto> obtenerAllOfertasByNegocio(String idNegocio) {

		return ofertasNegociosApiClient.obtenerOfertasByNegocio(idNegocio);

	}

	@Override
	public List<NegocioDto> obtenerNegociosByAccion(Integer idAccion) {

		List<NegocioDto> listNegocios = negocioRepository.obtenerNegociosByIdAccionAndEstatus(idAccion, Boolean.TRUE);

		if (listNegocios.isEmpty()) {
			throw new NegocioException("No se encontro ningun negocio registrado", "0");
		}

		List<NegocioDto> listNegocio2 = new ArrayList<NegocioDto>();

		for (NegocioDto negocioDto2 : listNegocios) {

			List<ServiciosDto> dataServiciosNegocio = negocioServiciosRepository
					.findServicios(negocioDto2.getIdNegocio());
			List<MetodoPagoDto> dataMetodosPagoNegocio = negocioMetodoPagoRepository
					.consultar(negocioDto2.getIdNegocio());
			List<TipoTarjetaDto> dataTipoTarjetaNEgocio = negocioTarjetasPagoRepository
					.findByIdNegocio(negocioDto2.getIdNegocio());

			negocioDto2.setServiciosList(dataServiciosNegocio);
			negocioDto2.setMetodoPagoList(dataMetodosPagoNegocio);
			negocioDto2.setTipoTarjetaList(dataTipoTarjetaNEgocio);

			listNegocio2.add(negocioDto2);
		}

		return listNegocio2;
	}

	@Override
	public List<NegocioDto> obtenerAllNegocios() {

		List<NegocioDto> listNegocio = negocioRepository.findAllNegociosByEstatus(Boolean.TRUE);
		if (listNegocio.isEmpty()) {
			throw new NegocioException("No se encontro ningun negocio registrado", "0");
		}

		List<NegocioDto> listNegocio2 = new ArrayList<NegocioDto>();

		for (NegocioDto negocioDto2 : listNegocio) {

			List<ServiciosDto> dataServiciosNegocio = negocioServiciosRepository
					.findServicios(negocioDto2.getIdNegocio());
			List<MetodoPagoDto> dataMetodosPagoNegocio = negocioMetodoPagoRepository
					.consultar(negocioDto2.getIdNegocio());
			List<TipoTarjetaDto> dataTipoTarjetaNEgocio = negocioTarjetasPagoRepository
					.findByIdNegocio(negocioDto2.getIdNegocio());

			negocioDto2.setServiciosList(dataServiciosNegocio);
			negocioDto2.setMetodoPagoList(dataMetodosPagoNegocio);
			negocioDto2.setTipoTarjetaList(dataTipoTarjetaNEgocio);

			listNegocio2.add(negocioDto2);
		}
		return listNegocio2;
	}

	@Override
	public List<NegocioDto> obtenerNegociosByNearby(double latitudActual, double longitudActual,double radio) {

		List<NegocioDto> negocioDtos  = negocioRepository.findAllNegociosByNearbyAndEstatusAndHorario(latitudActual, longitudActual, radio,Boolean.TRUE);
		
		if (negocioDtos.isEmpty()) {
			throw new NegocioException("No se encontro ningun negocio registrado", "0");
		}
		
		
		
		return negocioDtos;
	}

}
