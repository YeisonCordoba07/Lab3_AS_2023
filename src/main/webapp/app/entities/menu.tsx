import React from 'react';
import { Translate } from 'react-jhipster';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/materia-solicitud">
        <Translate contentKey="global.menu.entities.materiaSolicitud" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/solicitud-homologacion">
        <Translate contentKey="global.menu.entities.solicitudHomologacion" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/materia">
        <Translate contentKey="global.menu.entities.materia" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/relacion">
        <Translate contentKey="global.menu.entities.relacion" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/historia-academica">
        <Translate contentKey="global.menu.entities.historiaAcademica" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/estudiante">
        <Translate contentKey="global.menu.entities.estudiante" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/programa-academico">
        <Translate contentKey="global.menu.entities.programaAcademico" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/plan-estudios">
        <Translate contentKey="global.menu.entities.planEstudios" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/materia-semestre">
        <Translate contentKey="global.menu.entities.materiaSemestre" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/semestre">
        <Translate contentKey="global.menu.entities.semestre" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/estado-solicitud">
        <Translate contentKey="global.menu.entities.estadoSolicitud" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/estado-semestre">
        <Translate contentKey="global.menu.entities.estadoSemestre" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/tercio">
        <Translate contentKey="global.menu.entities.tercio" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/situacion-academica">
        <Translate contentKey="global.menu.entities.situacionAcademica" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/tipo-semestre">
        <Translate contentKey="global.menu.entities.tipoSemestre" />
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
