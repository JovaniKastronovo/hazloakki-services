SELECT id_negocio, nombre, id_categoria, email, descripcion, telefono, domicilio, latitud, longitud, estatus, codigo_postal, delegacion, colonia, calle, numero_exterior, horario, responsable, id_cuenta, id_accion, sitio_web
FROM public.negocio;


SELECT  id_negocio, nombre, descripcion, ( 6371 * ACOS(COS( RADIANS(19.3) )* COS(RADIANS( latitud ) )* COS(RADIANS( longitud )
- RADIANS(-99.1) )+ SIN( RADIANS(19.3) )* SIN(RADIANS( latitud ) ))) AS distance 
FROM negocio  
WHERE ESTATUS = true 
--HAVING distance < 1 
ORDER BY distance asc
--------------------------------------------------------------------------------------
select id_negocio, nombre, distance
from (
    select id_negocio, nombre ,( 6371 * acos( cos( radians(19.3206133) ) * 
    cos( radians( latitud ) ) * cos( radians( longitud ) - radians(-99.1531286) ) +
    sin( radians(19.3206133) ) * sin( radians( latitud ) ) ) ) / 1000 as distance
    from negocio
) as dt
where distance < 1
order by distance desc


SELECT u.id, u.nickname, d.web, d.age, d.avatar, g.description AS gender,
(6371 * ACOS(SIN(RADIANS(u.lat))* SIN(RADIANS('-12.0553442')) + COS(RADIANS(u.lng - '-77.0451853')) * COS(RADIANS(u.lat)) * COS(RADIANS('-12.0553442')))) AS distance 
FROM users AS u
LEFT JOIN user_data AS d ON (u.id = d.id)
LEFT JOIN genders AS g ON (g.id = d.gender)
WHERE (u.lat BETWEEN '-12.1003102803' AND '-12.0103781197')AND (u.lng BETWEEN '-77.0911654108' AND '-76.9992051892')
AND u.id NOT IN ('1')
HAVING distance <= '5'
ORDER BY distance asc


select a.id_negocio,a.nombre,b.distancia from 
	(SELECT * FROM negocio a) as a, 
	(SELECT b.id_negocio,(6371 * acos( cos( radians(19.3206133) ) *
		cos( radians( latitud ) ) *
		cos( radians( longitud ) -
		radians(-99.1531286) ) +
		sin( radians(19.3206133) ) *
		sin( radians( latitud ) ))) / 10 as distancia
	from negocio b) as b

where a.id_negocio = b.id_negocio
and distancia < 1
and a.estatus = true
order by b.distancia asc


select a.id_negocio,a.nombre,b.distancia from 
	(SELECT * FROM negocio a) as a, 
	(SELECT b.id_negocio,(6371 * ACOS( 
                                SIN(RADIANS(latitud)) * SIN(RADIANS(19.3206133)) 
                                + COS(RADIANS(longitud - -99.1531286)) * COS(RADIANS(latitud)) 
                                * COS(RADIANS(19.3206133))
                                )
                   ) / 1000 AS distancia
	from negocio b) as b
where a.id_negocio = b.id_negocio
HAVING distancia < 1 
order by b.distancia asc

-----------------


select id_negocio, nombre, latitud, longitud
from negocio 
order by geodistance(19.3206133, -99.1531286, latitud, longitud)


SELECT id, nombre, (6371 * ACOS( 
                                SIN(RADIANS(lat)) * SIN(RADIANS(4.6665578)) 
                                + COS(RADIANS(lng - -74.0524521)) * COS(RADIANS(lat)) 
                                * COS(RADIANS(4.6665578))
                                )
                   ) AS distance
FROM direcciones
HAVING distance < 1 /* 1 KM  a la redonda */
ORDER BY distance asc



SELECT id_negocio, nombre
FROM negocio
ORDER BY haversine(19.3206133, -99.1531286, latitud, longitud)

select id_negocio, nombre, (SELECT 6371 * acos( cos( radians(19.3206133) ) * 
cos( radians( latitud) ) * cos( radians( longitud ) - radians(-99.1531286) ) +
sin( radians(19.3206133) ) * sin( radians( latitud ) ) ) 
    from negocio
)AS dt

FROM negocio a
where a.id_negocio = dt.id_negocio
where distance < 1
order by distance desc





CREATE OR REPLACE FUNCTION haversine(latitude1 double precision,longitude1 double precision, latitude2 double precision, longitude2 double precision)
RETURNS double precision AS
$BODY$
	SELECT 6371 * acos( cos( radians(latitude1) ) * cos( radians( latitude2 ) ) * cos( radians( longitude1 ) - radians(longitude2) ) + sin( radians(latitude1) ) * sin( radians( latitude2 ) ) ) AS distance
$BODY$
LANGUAGE sql;


select id_negocio, nombre, distance
from (
    select id_negocio, nombre ,(SELECT 6371 * acos( cos( radians(19.3206133) ) *
    cos( radians( latitud ) ) * cos( radians( longitud ) -
    radians(-99.1531286) ) +
    sin( radians(19.3206133) ) *
    sin( radians( latitud )))) as distance
    from negocio
) as dt
--where distance < 1
--order by distance asc
order by haversine(19.3206133, -99.1531286, latitud, longitud)


CREATE OR REPLACE FUNCTION public.geodistance(alat double precision, alng double precision, blat double precision, blng double precision)
  RETURNS double precision AS
$BODY$
SELECT asin(
  sqrt(
    sin(radians($3-$1)/2)^2 +
    sin(radians($4-$2)/2)^2 *
    cos(radians($1)) *
    cos(radians($3))
  )
) * 7926.3352 AS distance;
$BODY$
  LANGUAGE sql IMMUTABLE
COST 100;

------------


select id_negocio, nombre, distance
from (
    select id_negocio, nombre ,(SELECT 6371 * acos( cos( radians(19.3206133) ) *
    cos( radians( latitud ) ) * cos( radians( longitud ) -
    radians(-99.1531286) ) +
    sin( radians(19.3206133) ) *
    sin( radians( latitud )))) as distance
    from negocio
) as dt
--where distance < 1
order by distance asc

---

    SELECT *, (  
        6371 * acos(cos(radians(19.3206133)) * cos(radians(latitud) ) *   
        cos(radians(longitud) - radians(-99.1531286)) + sin(radians(19.3206133)) * sin(radians(latitud)))  
    ) AS distance  
    FROM negocio  
    WHERE latitud != 19.3206133 
    AND longitud != -99.1531286 
    AND distance < 1  
    ORDER BY distance  
 
-----------

    
    select a.id_negocio,a.nombre,b.distancia 
    from 	(SELECT * FROM negocio a) as a, 	
    (SELECT b.id_negocio,(6371 * acos( cos( radians(19.3206133) ) * 		
    cos( radians( latitud ) ) * 		cos( radians( longitud ) - 		
    radians(-99.1531286) ) + 		sin( radians(19.3206133) ) * 		
    sin( radians( latitud ) ))) / 10 as distancia 	
    from negocio b) as b 
    where a.id_negocio = b.id_negocio 
    and a.ESTATUS = true 
    and distancia < 1 
    order by b.distancia asc 


    
    select a.id_negocio,a.nombre,b.distancia from 
	(SELECT * FROM negocio a) as a, 
	(SELECT b.id_negocio,(6371 * acos( cos( radians(19.3206133) ) *
		cos( radians( latitud ) ) *
		cos( radians( longitud ) -
		radians(-99.1531286) ) +
		sin( radians(19.3206133) ) *
		sin( radians( latitud ) ))) / 10 as distancia
	from negocio b) as b

where a.id_negocio = b.id_negocio
and distancia < 6
and a.estatus = true
order by b.distancia asc