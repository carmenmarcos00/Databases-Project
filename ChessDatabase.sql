-----------------------------------------------------
-- Script de generacion de la base de datos
-- EJECUTAR TODO EL CODIGO "DE GOLPE"
-- Base de datos: iChessProyecto (Ajedrez)
-- @asignatura: Base de datos
-- @titulacion: Ingenieria Informatica
-- @curso: 2019-2020
-- @autores: Pablo Almohalla Gomez, Susana Rebolledo, Alvaro Lopez Garcia, Carmen Marcos
-----------------------------------------------------

use master;
go

drop database if exists iChessProyecto;
go

create database iChessProyecto;
go

use iChessProyecto;
go

--TABLAS

/*
* Tabla para almacenar las nacionalidades
*/
create table nacionalidad(
	idNacionalidad int identity not null primary key,
	pais varchar(50) not null
);
go

/*
* Tabla para almacenar los torneos
*/
create table torneo(
	idTorneo char(3) not null primary key,
	nombre varchar(40) not null,
	fechaLimiteInscripcion datetime not null check(fechaLimiteInscripcion > getdate()),
	fechaFinTorneo datetime null,
	edadMinima int not null check(edadMinima > 0),
	minimoAforo int not null check(minimoAforo > 0),
	maximoAforo int not null check(maximoAforo > 0),
	numRondas int not null default 8 check(numRondas > 0),
	check(maximoAforo >= minimoAforo)
);
go

/*
* Tabla para almacenar las personas
*/
create table persona(
	idPersona int identity not null primary key,
	nombre varchar(40) not null,
	apellido1 varchar(80) not null,
	apellido2 varchar(80) null,
	NIF varchar(13) not null unique,
	ELO int not null check(ELO >= 0 AND ELO < 5000) default 0,
	fechaNacimiento date not null check (fechaNacimiento < getdate()),
	idNacionalidad int not null foreign key references nacionalidad(idNacionalidad)
);
go

/*
* Tabla para almacenar los premios
*/
create table premio(
	idPremio int identity not null,
	idTorneo char(3) not null foreign key references torneo(idTorneo),
	cantidad smallmoney not null check(cantidad > 0),
	idGanador int null foreign key references persona(idPersona),
	primary key(idPremio, idTorneo)
);
go

/*
* Tabla para almacenar las inscripciones
*/
create table inscripcion(
	idInscripcion int identity not null primary key,
	idPersona int not null foreign key references persona(idPersona),
	idTorneo char(3) not null foreign key references torneo(idTorneo),
	fechaInscripcion datetime default getdate()
);
go

/*
* Tabla para almacenar los jueces
*/
create table juez(
	idJuez int identity not null primary key,
	idPersona int not null foreign key references persona(idPersona),
);
go

/*
* Tabla para almacenar las rondas
*/
create table ronda(
	idRonda int identity not null primary key,
	idTorneo char(3) foreign key references torneo(idTorneo),
	numPartidas int not null,
	fechaInicioRondaPrevista datetime not null default getdate(),
	numRonda int not null,
	fechaRondaFin datetime null
);
go

/*
* Tabla para almacenar las partidas
*/
create table partida(
	idPartida int identity not null primary key,
	idJugador1 int not null foreign key references persona(idPersona),
	idJugador2 int not null foreign key references persona(idPersona),
	numeroMesa int not null,
	movimientos varchar(255) null,
	resultadoJug1 int null check(resultadoJug1 like ('[0-1]')),
	resultadoJug2 int null check(resultadoJug2 like ('[0-1]')),
	idArbitro int not null foreign key references juez(idJuez),
	fechaFinPartida datetime null,
	fechaInicio datetime not null,
	idRonda int not null foreign key references ronda(idRonda),
	check(idJugador1 != idJugador2)
);
go

--TRIGGERS

CREATE OR ALTER TRIGGER controlAforo on inscripcion
    after insert, update
    as begin
	DECLARE @idTorneo char(3)
	DECLARE @numeroJugadoresEnTorneo int
	SELECT @idTorneo=t.idTorneo FROM torneo t INNER JOIN inserted i ON t.idTorneo=i.idTorneo

	SELECT @numeroJugadoresEnTorneo=COUNT(*) FROM inscripcion ins WHERE ins.idTorneo=@idTorneo

	IF exists((select i.idInscripcion from inserted i inner join torneo t on (i.idTorneo=t.idTorneo)  where (t.maximoAforo < @numeroJugadoresEnTorneo) ))
	BEGIN
		ROLLBACK TRANSACTION
		raiserror('Error. Aforo m�ximo del torneo ya alcanzado.',16,1)
	END
	RETURN
end
go

CREATE OR ALTER TRIGGER yaInscrito on inscripcion
after insert, update
as begin
	if (select count(*) from inserted i inner join inscripcion ins on ins.idTorneo=i.idTorneo where ins.idPersona=i.idPersona)>1 begin
		ROLLBACK TRANSACTION
		raiserror('Error. Persona ya inscrita en el torneo.',16,1)
	END
end
go

CREATE OR ALTER TRIGGER numMesaPorRondaEnTorneo on partida
after insert, update
as begin
	if (select COUNT(*) from partida p inner join inserted i on i.idRonda=p.idRonda where p.numeroMesa=i.numeroMesa)>1 begin
		ROLLBACK TRANSACTION
		raiserror('Error. Mesa ya ocupada.',16,1)
	END
end
go

CREATE OR ALTER TRIGGER tardeParaInscribirse on inscripcion
after insert, update
as begin
	if exists((select i.fechaInscripcion from inserted i inner join torneo t on (i.idTorneo=t.idTorneo)  where (i.fechaInscripcion > t.fechaLimiteInscripcion) )) begin
		ROLLBACK TRANSACTION
		raiserror('Error. La fecha limite de inscripcion ya ha sido superada.',16,1)
	END
end
go

CREATE OR ALTER TRIGGER noPuedeJugarSinEstarInscrito on partida
after insert, update
as begin
	if not exists((select i.idJugador1 from inserted i inner join ronda r on (r.idRonda=i.idRonda) inner join inscripcion p on (r.idTorneo=p.idTorneo) where (i.idJugador1=p.idPersona) )) begin
		ROLLBACK TRANSACTION
		raiserror('Error. Jugador 1 no est� inscrito.',16,1)
	END
	if not exists((select i.idJugador2 from inserted i inner join ronda r on (r.idRonda=i.idRonda) inner join inscripcion p on (r.idTorneo=p.idTorneo) where (i.idJugador2=p.idPersona) )) begin
		ROLLBACK TRANSACTION
		raiserror('Error. Jugador 2 no est� inscrito.',16,1)
	END
end
go

CREATE OR ALTER TRIGGER controlEdadMinima on inscripcion
    after insert, update
    as begin
	DECLARE @idTorneo char(3)
	DECLARE @edad int
	DECLARE @fechaNacimiento date
	SELECT @idTorneo=t.idTorneo FROM torneo t INNER JOIN inserted i ON t.idTorneo=i.idTorneo

	select @fechaNacimiento=p.fechaNacimiento from persona p inner join inserted i on (i.idPersona=p.idPersona)
	SET @edad= year(getdate())-year(@fechaNacimiento)

	IF ((select t.edadMinima from torneo t where t.idTorneo=@idTorneo) > @edad)
	BEGIN
		ROLLBACK TRANSACTION
		raiserror('Error. El jugador no cumple con la edad m�nima.',16,1)
	END
	RETURN
end
go

--FUNCIONES

--Funcion que calcula la puntuacion por partida
CREATE OR ALTER FUNCTION calcular_puntuacion_partida_jugador (@idPartida int, @idJugador int)
RETURNS decimal(2,1)
AS
BEGIN
	DECLARE @puntuacion decimal(2,1)
	DECLARE @result1 int
	DECLARE @result2 int

	SELECT @result1 = p.resultadoJug1 FROM partida p WHERE @idPartida = p.idPartida
	SELECT @result2 = p.resultadoJug2 FROM partida p WHERE @idPartida = p.idPartida

	IF (@result1 = @result2) --En caso de empate
	BEGIN
		SET @puntuacion = 0.5
		RETURN @puntuacion
	END

	IF ((SELECT p.idJugador1 FROM partida p WHERE @idPartida = p.idPartida) = @idJugador)
	BEGIN
		SET @puntuacion = @result1
		RETURN @puntuacion
	END

	SET @puntuacion = @result2
	RETURN @puntuacion
END
GO

--Funcion que calcula la puntuacion de un jugador en un torneo determinado
CREATE OR ALTER FUNCTION calcular_puntuacion_jugador_en_torneo (@NIF varchar(13), @idTorneo char(3))
RETURNS decimal(3,1)
AS
BEGIN
	DECLARE @puntuacion decimal(3,1)
	DECLARE @idPersona int
	DECLARE @existeJugEnTorneo int

	SELECT @idPersona = p.idPersona FROM persona p WHERE @NIF = p.NIF
	SELECT @existeJugEnTorneo = COUNT(*) FROM inscripcion i WHERE (@idTorneo = i.idTorneo AND @idPersona = i.idPersona) --Devuelve 0 si no existe, 1 si existe

	IF (@existeJugEnTorneo > 0) 
	BEGIN
		SELECT @puntuacion = SUM(dbo.calcular_puntuacion_partida_jugador(p.idPartida, @idPersona)) FROM partida p 
			INNER JOIN ronda r ON r.idTorneo = @idTorneo
			WHERE r.idRonda = p.idRonda
		IF (@puntuacion IS NULL)
		BEGIN
			SET @puntuacion = 0
		END
		RETURN @puntuacion
	END
	RETURN -1 --Devuelve -1 en caso de error
END
GO

--Funcion que calcula el ranking de un torneo
CREATE OR ALTER FUNCTION ranking_torneo(@idTorneo char(3))
RETURNS TABLE
AS
RETURN (SELECT TOP (SELECT COUNT(*) FROM persona INNER JOIN torneo t ON t.idTorneo = @idTorneo where idPersona not in (select idPersona from juez)) ROW_NUMBER() OVER(ORDER BY dbo.calcular_puntuacion_jugador_en_torneo(p.NIF, @idTorneo) DESC) AS Pos, p.*, dbo.calcular_puntuacion_jugador_en_torneo(p.NIF, @idTorneo) AS puntuacionTorneo FROM persona p 
	INNER JOIN inscripcion i ON i.idTorneo = @idTorneo WHERE p.idPersona = i.idPersona and p.idPersona not in (select idPersona from juez) ORDER BY puntuacionTorneo DESC)
GO

--Funcion que calcula el ranking inicial en funci�n del ELO
CREATE OR ALTER FUNCTION ranking_inicial(@idTorneo char(3))
RETURNS TABLE
AS
RETURN (SELECT TOP (SELECT COUNT(*) FROM persona INNER JOIN torneo t ON t.idTorneo = @idTorneo where idPersona not in (select idPersona from juez)) ROW_NUMBER() OVER(ORDER BY p.ELO DESC) AS Pos, p.* FROM persona p where idPersona not in (select idPersona from juez) ORDER BY p.ELO DESC)
GO

--Funci�n que devuelve la posici�n de un jugador en un torneo
CREATE OR ALTER FUNCTION getPosEnTorneo (@NIF varchar(13), @idTorneo char(3))
RETURNS decimal(3,1)
AS
BEGIN
	DECLARE @pos int
	SELECT @pos=r.Pos from dbo.ranking_torneo(@idTorneo) r where r.NIF=@NIF
	RETURN @pos
END
GO

--Funcion que devuelve las estad�sticas de un jugador
CREATE OR ALTER FUNCTION estadisticas_jugador_en_torneo(@NIF varchar(13), @idTorneo char(3))
RETURNS TABLE
AS
RETURN (SELECT dbo.getPosEnTorneo(@NIF, @idTorneo) AS Pos, dbo.calcular_puntuacion_jugador_en_torneo(p.NIF, i.idTorneo) as puntuacionEnTorneo, p.* FROM persona p INNER JOIN inscripcion i ON (i.idPersona = p.idPersona AND i.idTorneo = @idTorneo) WHERE p.NIF = @NIF)
GO

--Funci�n que devuelve los datos de todos los torneos
CREATE OR ALTER FUNCTION datos_torneos()
RETURNS TABLE
AS
RETURN (SELECT t.* FROM torneo t)
GO

--Funci�n que devuelve los posibles contrincantes para la siguiente ronda de un jugador
CREATE OR ALTER FUNCTION posibles_contrincantes_para_jugador(@NIF varchar(13), @idTorneo char(3))
RETURNS TABLE
AS
RETURN ((SELECT p.* FROM persona p INNER JOIN inscripcion i ON i.idTorneo=@idTorneo WHERE (p.idPersona=i.idPersona AND p.NIF!=@NIF AND p.idPersona NOT IN (SELECT j.idPersona FROM juez j) AND p.idPersona NOT IN ((SELECT pa.idJugador1 FROM partida pa INNER JOIN torneo t ON t.idTorneo=@idTorneo INNER JOIN ronda r ON r.idRonda=pa.idRonda AND @idTorneo=r.idTorneo INNER JOIN persona p ON p.NIF=@NIF WHERE pa.idJugador2=p.idPersona) 
	UNION (SELECT pa.idJugador2 FROM partida pa INNER JOIN torneo t ON t.idTorneo=@idTorneo INNER JOIN ronda r ON r.idRonda=pa.idRonda AND @idTorneo=r.idTorneo INNER JOIN persona p ON p.NIF=@NIF WHERE pa.idJugador1=p.idPersona))))) 
GO

--PROCEDIMIENTOS

CREATE PROCEDURE usp_showerrorinfo
AS
SELECT ERROR_NUMBER() AS [Numero de Error],
	ERROR_STATE() AS [Estado del Error],
	ERROR_SEVERITY() AS [Severidad del Error],
	ERROR_LINE() AS [Linea],
	ISNULL(ERROR_PROCEDURE(), 'No esta en un proc') AS [Procedimiento],
	ERROR_MESSAGE() AS [Mensaje]
GO

CREATE OR ALTER PROCEDURE iniciaPartida @idTorneo char(3), @numRonda int, @numMesa int, @NIFjug1 varchar(13), @NIFjug2 varchar(13), @NIFjuez varchar(13), @error int out AS
BEGIN TRY
    BEGIN TRANSACTION
		DECLARE @idJug1 int
		DECLARE @idJug2 int
		DECLARE @idRonda int
		DECLARE @idJuez int
		IF @numRonda NOT IN (SELECT r.numRonda FROM ronda r INNER JOIN torneo t ON t.idTorneo=r.idTorneo where r.idTorneo=@idTorneo)
			BEGIN
				RAISERROR('El numero de ronda seleccionado no existe.',16,1)
			END
		IF @NIFjug1 NOT IN (SELECT p.NIF FROM persona p)
			BEGIN
				RAISERROR('No existe el jugador.',16,1)
			END
		IF @NIFjug2 NOT IN (SELECT p.NIF FROM persona p)
			BEGIN
				RAISERROR('No existe el jugador.',16,1)
			END
		IF @NIFjuez NOT IN (SELECT p.NIF FROM persona p)
			BEGIN
				RAISERROR('No existe el juez.',16,1)
			END

		SELECT @idRonda=r.idRonda FROM ronda r inner join torneo t on t.idTorneo=@idTorneo where r.numRonda=@numRonda
		SELECT @idJug1=p.idPersona FROM persona p INNER JOIN inscripcion i ON i.idTorneo=@idTorneo WHERE p.NIF=@NIFjug1
		SELECT @idJug2=p.idPersona FROM persona p INNER JOIN inscripcion i ON i.idTorneo=@idTorneo WHERE p.NIF=@NIFjug2
		SELECT @idJuez=j.idJuez FROM juez j INNER JOIN persona p on p.idPersona=j.idPersona INNER JOIN inscripcion i ON i.idTorneo=@idTorneo WHERE p.NIF=@NIFjuez
		
		IF @idJuez NOT IN (SELECT j.idJuez FROM juez j)
			BEGIN
				RAISERROR('El NIF del juez no corresponde con un juez.',16,1)
			END
		IF @idJug1 IS NULL
			BEGIN
				RAISERROR('El jugador 1 no est� inscrito en el torneo.',16,1)
			END
		IF @idJug2 IS NULL
			BEGIN
				RAISERROR('El jugador 2 no est� inscrito en el torneo.',16,1)
			END

		INSERT INTO partida(idJugador1, idJugador2, numeroMesa, idArbitro, fechaInicio, idRonda) VALUES(@idJug1,@idJug2,@numMesa,@idJuez,getdate(),@idRonda);

		SET @error=0
	COMMIT TRANSACTION
END TRY
BEGIN CATCH
        ROLLBACK TRANSACTION
		SET @error=-1
        EXEC usp_showerrorinfo
END CATCH
RETURN
GO

CREATE OR ALTER PROCEDURE finalizaRonda @numRonda int, @idTorneo varchar(3), @error int out AS
BEGIN TRY
    BEGIN TRANSACTION
		DECLARE @nulos int
		DECLARE @idRonda int
		IF @idTorneo NOT IN (SELECT t.idTorneo FROM torneo t)
			BEGIN
				RAISERROR('El torneo seleccionado no existe.',16,1)
			END
		IF @numRonda NOT IN (SELECT r.numRonda FROM ronda r INNER JOIN torneo t ON t.idTorneo=r.idTorneo where r.idTorneo=@idTorneo)
			BEGIN
				RAISERROR('El numero de ronda seleccionado no existe.',16,1)
			END
		SELECT @nulos=COUNT(*) FROM partida pa INNER JOIN ronda r ON r.idRonda=pa.idRonda INNER JOIN torneo t ON t.idTorneo=@idTorneo WHERE (pa.resultadoJug1 IS NULL OR pa.resultadoJug2 IS NULL)
		IF @nulos > 0
			BEGIN
				RAISERROR('Todav�a hay partidas en juego.',16,1)
			END

		SELECT @idRonda=r.idRonda FROM ronda r inner join torneo t on t.idTorneo=@idTorneo where r.numRonda=@numRonda

		UPDATE ronda SET fechaRondaFin=GETDATE() WHERE idRonda=@idRonda
		SET @error=0
	COMMIT TRANSACTION
END TRY
BEGIN CATCH
        ROLLBACK TRANSACTION
		SET @error=-1
        EXEC usp_showerrorinfo
END CATCH
RETURN
GO

CREATE OR ALTER PROCEDURE registraResultado @idTorneo char(3), @numMesa int, @numRonda int, @resultadoJug1 int, @resultadoJug2 int, @movimientos varchar(255), @error int out AS
BEGIN TRY
    BEGIN TRANSACTION

		DECLARE @idPartida int
		DECLARE @idRonda int

		IF @idTorneo NOT IN (SELECT t.idTorneo FROM torneo t)
			BEGIN
				RAISERROR('El torneo seleccionado no existe.',16,1)
			END
		IF @numRonda NOT IN (SELECT r.numRonda FROM ronda r INNER JOIN torneo t ON t.idTorneo=r.idTorneo where r.idTorneo=@idTorneo)
			BEGIN
				RAISERROR('El numero de ronda seleccionado no existe.',16,1)
			END
		IF @numMesa NOT IN (SELECT p.numeroMesa FROM partida p INNER JOIN ronda r on r.idRonda=p.idRonda INNER JOIN torneo t ON t.idTorneo=r.idTorneo where r.idTorneo=@idTorneo)
			BEGIN
				RAISERROR('El numero de mesa seleccionado no existe.',16,1)
			END

		SELECT @idRonda=r.idRonda FROM ronda r inner join torneo t on t.idTorneo=@idTorneo where r.numRonda=@numRonda
		SELECT @idPartida= p.idPartida from partida p where p.idRonda=@idRonda
		UPDATE partida SET resultadoJug1=@resultadoJug1 WHERE idPartida=@idPartida
		UPDATE partida SET resultadoJug2=@resultadoJug2 WHERE idPartida=@idPartida
		UPDATE partida SET movimientos=@movimientos WHERE idPartida=@idPartida
		UPDATE partida SET fechaFinPartida=getdate() WHERE idPartida=@idPartida
		SET @error=0
	COMMIT TRANSACTION
END TRY
BEGIN CATCH
        ROLLBACK TRANSACTION
		SET @error=-1
        EXEC usp_showerrorinfo
END CATCH
RETURN
GO

CREATE OR ALTER PROCEDURE iniciaRonda @idTorneo char(3), @numRonda int, @error int out AS
BEGIN TRY
    BEGIN TRANSACTION
		DECLARE @numInscritos int
		DECLARE @numPartidas int

		IF @idTorneo NOT IN (SELECT t.idTorneo FROM torneo t)
			BEGIN
				RAISERROR('El torneo seleccionado no existe.',16,1)
			END
		SELECT @numInscritos=count(*) from inscripcion i inner join persona p on p.idPersona=i.idPersona 
			where (i.idTorneo=@idTorneo and p.idPersona not in (select j.idPersona from juez j))
		SET @numPartidas=(@numInscritos/2)
		INSERT INTO ronda VALUES(@idTorneo,@numPartidas,getdate(),@numRonda,NULL);

		SET @error=0
	COMMIT TRANSACTION
END TRY
BEGIN CATCH
        ROLLBACK TRANSACTION
		SET @error=-1
        EXEC usp_showerrorinfo
END CATCH
RETURN
GO

CREATE OR ALTER PROCEDURE finalizaTorneo @idTorneo char(3), @error int out AS
BEGIN TRY
    BEGIN TRANSACTION
		DECLARE @idGanador1 int
		DECLARE @idGanador2 int
		DECLARE @idGanador3 int
		DECLARE @idPremio1 int
		DECLARE @idPremio2 int
		DECLARE @idPremio3 int

		IF @idTorneo NOT IN (SELECT t.idTorneo FROM torneo t)
			BEGIN
				RAISERROR('El torneo seleccionado no existe.',16,1)
			END
		SELECT @idGanador1=r.idPersona FROM dbo.ranking_torneo(@idTorneo) r WHERE r.Pos=1
		SELECT @idGanador2=r.idPersona FROM dbo.ranking_torneo(@idTorneo) r WHERE r.Pos=2
		SELECT @idGanador3=r.idPersona FROM dbo.ranking_torneo(@idTorneo) r WHERE r.Pos=3

		IF (@idGanador1 IS NULL OR @idGanador2 IS NULL OR @idGanador3 IS NULL)
			BEGIN
				RAISERROR('Error al obtener algun ganador.',16,1)
			END

		SELECT TOP 1 @idPremio1 = p.idPremio FROM premio p WHERE p.idTorneo=@idTorneo ORDER BY p.cantidad DESC
		SELECT TOP 1 @idPremio2 = p.idPremio FROM premio p WHERE p.idTorneo=@idTorneo and p.idPremio!=@idPremio1 ORDER BY p.cantidad DESC
		SELECT TOP 1 @idPremio3 = p.idPremio FROM premio p WHERE p.idTorneo=@idTorneo and p.idPremio!=@idPremio1 and p.idPremio!=@idPremio2 ORDER BY p.cantidad DESC

		IF (@idPremio1 IS NULL OR @idPremio2 IS NULL OR @idPremio3 IS NULL)
			BEGIN
				RAISERROR('Error al obtener algun premio.',16,1)
			END

		UPDATE premio SET idGanador=@idGanador1 WHERE idPremio=@idPremio1
		UPDATE premio SET idGanador=@idGanador2 WHERE idPremio=@idPremio2
		UPDATE premio SET idGanador=@idGanador3 WHERE idPremio=@idPremio3

		UPDATE torneo SET fechaFinTorneo=getdate() WHERE idTorneo=@idTorneo

		UPDATE p SET ELO=ELO + 2*(select dbo.calcular_puntuacion_jugador_en_torneo(p.NIF,@idTorneo)) from persona p inner join inscripcion i on i.idPersona=p.idPersona
		SET @error=0
	COMMIT TRANSACTION
END TRY
BEGIN CATCH
        ROLLBACK TRANSACTION
		SET @error=-1
        EXEC usp_showerrorinfo
END CATCH
RETURN
GO

--INSERTS PARA UNA FUNCIONALIDAD B�SICA

INSERT INTO nacionalidad VALUES('ESPA�A')
INSERT INTO nacionalidad VALUES('FRANCIA')
INSERT INTO nacionalidad VALUES('PORTUGAL')