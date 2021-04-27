package proquinal.com.web.rest;

import proquinal.com.ProquiEquiposVentaApp;
import proquinal.com.domain.Equipo;
import proquinal.com.repository.EquipoRepository;
import proquinal.com.service.EquipoService;
import proquinal.com.service.dto.EquipoDTO;
import proquinal.com.service.mapper.EquipoMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import proquinal.com.domain.enumeration.State;
/**
 * Integration tests for the {@link EquipoResource} REST controller.
 */
@SpringBootTest(classes = ProquiEquiposVentaApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class EquipoResourceIT {

    private static final String DEFAULT_ACTIVO_FIJO = "AAAAAAAAAA";
    private static final String UPDATED_ACTIVO_FIJO = "BBBBBBBBBB";

    private static final String DEFAULT_MARCA = "AAAAAAAAAA";
    private static final String UPDATED_MARCA = "BBBBBBBBBB";

    private static final String DEFAULT_PROCESADOR = "AAAAAAAAAA";
    private static final String UPDATED_PROCESADOR = "BBBBBBBBBB";

    private static final String DEFAULT_OFFICE = "AAAAAAAAAA";
    private static final String UPDATED_OFFICE = "BBBBBBBBBB";

    private static final String DEFAULT_SISTEMA_OPERATIVO = "AAAAAAAAAA";
    private static final String UPDATED_SISTEMA_OPERATIVO = "BBBBBBBBBB";

    private static final String DEFAULT_DISCO_DURO = "AAAAAAAAAA";
    private static final String UPDATED_DISCO_DURO = "BBBBBBBBBB";

    private static final String DEFAULT_RAM = "AAAAAAAAAA";
    private static final String UPDATED_RAM = "BBBBBBBBBB";

    private static final String DEFAULT_OBSERVACIONES = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVACIONES = "BBBBBBBBBB";

    private static final byte[] DEFAULT_IMG_URL = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMG_URL = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMG_URL_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMG_URL_CONTENT_TYPE = "image/png";

    private static final State DEFAULT_TIPO = State.ESCRITORIO;
    private static final State UPDATED_TIPO = State.PORTATIL;

    private static final String DEFAULT_WINDOWSS = "AAAAAAAAAA";
    private static final String UPDATED_WINDOWSS = "BBBBBBBBBB";

    private static final String DEFAULT_PRECIO = "AAAAAAAAAA";
    private static final String UPDATED_PRECIO = "BBBBBBBBBB";

    @Autowired
    private EquipoRepository equipoRepository;

    @Autowired
    private EquipoMapper equipoMapper;

    @Autowired
    private EquipoService equipoService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEquipoMockMvc;

    private Equipo equipo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Equipo createEntity(EntityManager em) {
        Equipo equipo = new Equipo()
            .activoFijo(DEFAULT_ACTIVO_FIJO)
            .marca(DEFAULT_MARCA)
            .procesador(DEFAULT_PROCESADOR)
            .office(DEFAULT_OFFICE)
            .sistemaOperativo(DEFAULT_SISTEMA_OPERATIVO)
            .discoDuro(DEFAULT_DISCO_DURO)
            .ram(DEFAULT_RAM)
            .observaciones(DEFAULT_OBSERVACIONES)
            .imgUrl(DEFAULT_IMG_URL)
            .imgUrlContentType(DEFAULT_IMG_URL_CONTENT_TYPE)
            .tipo(DEFAULT_TIPO)
            .windowss(DEFAULT_WINDOWSS)
            .precio(DEFAULT_PRECIO);
        return equipo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Equipo createUpdatedEntity(EntityManager em) {
        Equipo equipo = new Equipo()
            .activoFijo(UPDATED_ACTIVO_FIJO)
            .marca(UPDATED_MARCA)
            .procesador(UPDATED_PROCESADOR)
            .office(UPDATED_OFFICE)
            .sistemaOperativo(UPDATED_SISTEMA_OPERATIVO)
            .discoDuro(UPDATED_DISCO_DURO)
            .ram(UPDATED_RAM)
            .observaciones(UPDATED_OBSERVACIONES)
            .imgUrl(UPDATED_IMG_URL)
            .imgUrlContentType(UPDATED_IMG_URL_CONTENT_TYPE)
            .tipo(UPDATED_TIPO)
            .windowss(UPDATED_WINDOWSS)
            .precio(UPDATED_PRECIO);
        return equipo;
    }

    @BeforeEach
    public void initTest() {
        equipo = createEntity(em);
    }

    @Test
    @Transactional
    public void createEquipo() throws Exception {
        int databaseSizeBeforeCreate = equipoRepository.findAll().size();

        // Create the Equipo
        EquipoDTO equipoDTO = equipoMapper.toDto(equipo);
        restEquipoMockMvc.perform(post("/api/equipos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(equipoDTO)))
            .andExpect(status().isCreated());

        // Validate the Equipo in the database
        List<Equipo> equipoList = equipoRepository.findAll();
        assertThat(equipoList).hasSize(databaseSizeBeforeCreate + 1);
        Equipo testEquipo = equipoList.get(equipoList.size() - 1);
        assertThat(testEquipo.getActivoFijo()).isEqualTo(DEFAULT_ACTIVO_FIJO);
        assertThat(testEquipo.getMarca()).isEqualTo(DEFAULT_MARCA);
        assertThat(testEquipo.getProcesador()).isEqualTo(DEFAULT_PROCESADOR);
        assertThat(testEquipo.getOffice()).isEqualTo(DEFAULT_OFFICE);
        assertThat(testEquipo.getSistemaOperativo()).isEqualTo(DEFAULT_SISTEMA_OPERATIVO);
        assertThat(testEquipo.getDiscoDuro()).isEqualTo(DEFAULT_DISCO_DURO);
        assertThat(testEquipo.getRam()).isEqualTo(DEFAULT_RAM);
        assertThat(testEquipo.getObservaciones()).isEqualTo(DEFAULT_OBSERVACIONES);
        assertThat(testEquipo.getImgUrl()).isEqualTo(DEFAULT_IMG_URL);
        assertThat(testEquipo.getImgUrlContentType()).isEqualTo(DEFAULT_IMG_URL_CONTENT_TYPE);
        assertThat(testEquipo.getTipo()).isEqualTo(DEFAULT_TIPO);
        assertThat(testEquipo.getWindowss()).isEqualTo(DEFAULT_WINDOWSS);
        assertThat(testEquipo.getPrecio()).isEqualTo(DEFAULT_PRECIO);
    }

    @Test
    @Transactional
    public void createEquipoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = equipoRepository.findAll().size();

        // Create the Equipo with an existing ID
        equipo.setId(1L);
        EquipoDTO equipoDTO = equipoMapper.toDto(equipo);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEquipoMockMvc.perform(post("/api/equipos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(equipoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Equipo in the database
        List<Equipo> equipoList = equipoRepository.findAll();
        assertThat(equipoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkActivoFijoIsRequired() throws Exception {
        int databaseSizeBeforeTest = equipoRepository.findAll().size();
        // set the field null
        equipo.setActivoFijo(null);

        // Create the Equipo, which fails.
        EquipoDTO equipoDTO = equipoMapper.toDto(equipo);

        restEquipoMockMvc.perform(post("/api/equipos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(equipoDTO)))
            .andExpect(status().isBadRequest());

        List<Equipo> equipoList = equipoRepository.findAll();
        assertThat(equipoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMarcaIsRequired() throws Exception {
        int databaseSizeBeforeTest = equipoRepository.findAll().size();
        // set the field null
        equipo.setMarca(null);

        // Create the Equipo, which fails.
        EquipoDTO equipoDTO = equipoMapper.toDto(equipo);

        restEquipoMockMvc.perform(post("/api/equipos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(equipoDTO)))
            .andExpect(status().isBadRequest());

        List<Equipo> equipoList = equipoRepository.findAll();
        assertThat(equipoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkProcesadorIsRequired() throws Exception {
        int databaseSizeBeforeTest = equipoRepository.findAll().size();
        // set the field null
        equipo.setProcesador(null);

        // Create the Equipo, which fails.
        EquipoDTO equipoDTO = equipoMapper.toDto(equipo);

        restEquipoMockMvc.perform(post("/api/equipos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(equipoDTO)))
            .andExpect(status().isBadRequest());

        List<Equipo> equipoList = equipoRepository.findAll();
        assertThat(equipoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOfficeIsRequired() throws Exception {
        int databaseSizeBeforeTest = equipoRepository.findAll().size();
        // set the field null
        equipo.setOffice(null);

        // Create the Equipo, which fails.
        EquipoDTO equipoDTO = equipoMapper.toDto(equipo);

        restEquipoMockMvc.perform(post("/api/equipos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(equipoDTO)))
            .andExpect(status().isBadRequest());

        List<Equipo> equipoList = equipoRepository.findAll();
        assertThat(equipoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSistemaOperativoIsRequired() throws Exception {
        int databaseSizeBeforeTest = equipoRepository.findAll().size();
        // set the field null
        equipo.setSistemaOperativo(null);

        // Create the Equipo, which fails.
        EquipoDTO equipoDTO = equipoMapper.toDto(equipo);

        restEquipoMockMvc.perform(post("/api/equipos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(equipoDTO)))
            .andExpect(status().isBadRequest());

        List<Equipo> equipoList = equipoRepository.findAll();
        assertThat(equipoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDiscoDuroIsRequired() throws Exception {
        int databaseSizeBeforeTest = equipoRepository.findAll().size();
        // set the field null
        equipo.setDiscoDuro(null);

        // Create the Equipo, which fails.
        EquipoDTO equipoDTO = equipoMapper.toDto(equipo);

        restEquipoMockMvc.perform(post("/api/equipos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(equipoDTO)))
            .andExpect(status().isBadRequest());

        List<Equipo> equipoList = equipoRepository.findAll();
        assertThat(equipoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRamIsRequired() throws Exception {
        int databaseSizeBeforeTest = equipoRepository.findAll().size();
        // set the field null
        equipo.setRam(null);

        // Create the Equipo, which fails.
        EquipoDTO equipoDTO = equipoMapper.toDto(equipo);

        restEquipoMockMvc.perform(post("/api/equipos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(equipoDTO)))
            .andExpect(status().isBadRequest());

        List<Equipo> equipoList = equipoRepository.findAll();
        assertThat(equipoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTipoIsRequired() throws Exception {
        int databaseSizeBeforeTest = equipoRepository.findAll().size();
        // set the field null
        equipo.setTipo(null);

        // Create the Equipo, which fails.
        EquipoDTO equipoDTO = equipoMapper.toDto(equipo);

        restEquipoMockMvc.perform(post("/api/equipos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(equipoDTO)))
            .andExpect(status().isBadRequest());

        List<Equipo> equipoList = equipoRepository.findAll();
        assertThat(equipoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkWindowssIsRequired() throws Exception {
        int databaseSizeBeforeTest = equipoRepository.findAll().size();
        // set the field null
        equipo.setWindowss(null);

        // Create the Equipo, which fails.
        EquipoDTO equipoDTO = equipoMapper.toDto(equipo);

        restEquipoMockMvc.perform(post("/api/equipos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(equipoDTO)))
            .andExpect(status().isBadRequest());

        List<Equipo> equipoList = equipoRepository.findAll();
        assertThat(equipoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrecioIsRequired() throws Exception {
        int databaseSizeBeforeTest = equipoRepository.findAll().size();
        // set the field null
        equipo.setPrecio(null);

        // Create the Equipo, which fails.
        EquipoDTO equipoDTO = equipoMapper.toDto(equipo);

        restEquipoMockMvc.perform(post("/api/equipos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(equipoDTO)))
            .andExpect(status().isBadRequest());

        List<Equipo> equipoList = equipoRepository.findAll();
        assertThat(equipoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEquipos() throws Exception {
        // Initialize the database
        equipoRepository.saveAndFlush(equipo);

        // Get all the equipoList
        restEquipoMockMvc.perform(get("/api/equipos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(equipo.getId().intValue())))
            .andExpect(jsonPath("$.[*].activoFijo").value(hasItem(DEFAULT_ACTIVO_FIJO)))
            .andExpect(jsonPath("$.[*].marca").value(hasItem(DEFAULT_MARCA)))
            .andExpect(jsonPath("$.[*].procesador").value(hasItem(DEFAULT_PROCESADOR)))
            .andExpect(jsonPath("$.[*].office").value(hasItem(DEFAULT_OFFICE)))
            .andExpect(jsonPath("$.[*].sistemaOperativo").value(hasItem(DEFAULT_SISTEMA_OPERATIVO)))
            .andExpect(jsonPath("$.[*].discoDuro").value(hasItem(DEFAULT_DISCO_DURO)))
            .andExpect(jsonPath("$.[*].ram").value(hasItem(DEFAULT_RAM)))
            .andExpect(jsonPath("$.[*].observaciones").value(hasItem(DEFAULT_OBSERVACIONES)))
            .andExpect(jsonPath("$.[*].imgUrlContentType").value(hasItem(DEFAULT_IMG_URL_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imgUrl").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMG_URL))))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO.toString())))
            .andExpect(jsonPath("$.[*].windowss").value(hasItem(DEFAULT_WINDOWSS)))
            .andExpect(jsonPath("$.[*].precio").value(hasItem(DEFAULT_PRECIO)));
    }
    
    @Test
    @Transactional
    public void getEquipo() throws Exception {
        // Initialize the database
        equipoRepository.saveAndFlush(equipo);

        // Get the equipo
        restEquipoMockMvc.perform(get("/api/equipos/{id}", equipo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(equipo.getId().intValue()))
            .andExpect(jsonPath("$.activoFijo").value(DEFAULT_ACTIVO_FIJO))
            .andExpect(jsonPath("$.marca").value(DEFAULT_MARCA))
            .andExpect(jsonPath("$.procesador").value(DEFAULT_PROCESADOR))
            .andExpect(jsonPath("$.office").value(DEFAULT_OFFICE))
            .andExpect(jsonPath("$.sistemaOperativo").value(DEFAULT_SISTEMA_OPERATIVO))
            .andExpect(jsonPath("$.discoDuro").value(DEFAULT_DISCO_DURO))
            .andExpect(jsonPath("$.ram").value(DEFAULT_RAM))
            .andExpect(jsonPath("$.observaciones").value(DEFAULT_OBSERVACIONES))
            .andExpect(jsonPath("$.imgUrlContentType").value(DEFAULT_IMG_URL_CONTENT_TYPE))
            .andExpect(jsonPath("$.imgUrl").value(Base64Utils.encodeToString(DEFAULT_IMG_URL)))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO.toString()))
            .andExpect(jsonPath("$.windowss").value(DEFAULT_WINDOWSS))
            .andExpect(jsonPath("$.precio").value(DEFAULT_PRECIO));
    }

    @Test
    @Transactional
    public void getNonExistingEquipo() throws Exception {
        // Get the equipo
        restEquipoMockMvc.perform(get("/api/equipos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEquipo() throws Exception {
        // Initialize the database
        equipoRepository.saveAndFlush(equipo);

        int databaseSizeBeforeUpdate = equipoRepository.findAll().size();

        // Update the equipo
        Equipo updatedEquipo = equipoRepository.findById(equipo.getId()).get();
        // Disconnect from session so that the updates on updatedEquipo are not directly saved in db
        em.detach(updatedEquipo);
        updatedEquipo
            .activoFijo(UPDATED_ACTIVO_FIJO)
            .marca(UPDATED_MARCA)
            .procesador(UPDATED_PROCESADOR)
            .office(UPDATED_OFFICE)
            .sistemaOperativo(UPDATED_SISTEMA_OPERATIVO)
            .discoDuro(UPDATED_DISCO_DURO)
            .ram(UPDATED_RAM)
            .observaciones(UPDATED_OBSERVACIONES)
            .imgUrl(UPDATED_IMG_URL)
            .imgUrlContentType(UPDATED_IMG_URL_CONTENT_TYPE)
            .tipo(UPDATED_TIPO)
            .windowss(UPDATED_WINDOWSS)
            .precio(UPDATED_PRECIO);
        EquipoDTO equipoDTO = equipoMapper.toDto(updatedEquipo);

        restEquipoMockMvc.perform(put("/api/equipos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(equipoDTO)))
            .andExpect(status().isOk());

        // Validate the Equipo in the database
        List<Equipo> equipoList = equipoRepository.findAll();
        assertThat(equipoList).hasSize(databaseSizeBeforeUpdate);
        Equipo testEquipo = equipoList.get(equipoList.size() - 1);
        assertThat(testEquipo.getActivoFijo()).isEqualTo(UPDATED_ACTIVO_FIJO);
        assertThat(testEquipo.getMarca()).isEqualTo(UPDATED_MARCA);
        assertThat(testEquipo.getProcesador()).isEqualTo(UPDATED_PROCESADOR);
        assertThat(testEquipo.getOffice()).isEqualTo(UPDATED_OFFICE);
        assertThat(testEquipo.getSistemaOperativo()).isEqualTo(UPDATED_SISTEMA_OPERATIVO);
        assertThat(testEquipo.getDiscoDuro()).isEqualTo(UPDATED_DISCO_DURO);
        assertThat(testEquipo.getRam()).isEqualTo(UPDATED_RAM);
        assertThat(testEquipo.getObservaciones()).isEqualTo(UPDATED_OBSERVACIONES);
        assertThat(testEquipo.getImgUrl()).isEqualTo(UPDATED_IMG_URL);
        assertThat(testEquipo.getImgUrlContentType()).isEqualTo(UPDATED_IMG_URL_CONTENT_TYPE);
        assertThat(testEquipo.getTipo()).isEqualTo(UPDATED_TIPO);
        assertThat(testEquipo.getWindowss()).isEqualTo(UPDATED_WINDOWSS);
        assertThat(testEquipo.getPrecio()).isEqualTo(UPDATED_PRECIO);
    }

    @Test
    @Transactional
    public void updateNonExistingEquipo() throws Exception {
        int databaseSizeBeforeUpdate = equipoRepository.findAll().size();

        // Create the Equipo
        EquipoDTO equipoDTO = equipoMapper.toDto(equipo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEquipoMockMvc.perform(put("/api/equipos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(equipoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Equipo in the database
        List<Equipo> equipoList = equipoRepository.findAll();
        assertThat(equipoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEquipo() throws Exception {
        // Initialize the database
        equipoRepository.saveAndFlush(equipo);

        int databaseSizeBeforeDelete = equipoRepository.findAll().size();

        // Delete the equipo
        restEquipoMockMvc.perform(delete("/api/equipos/{id}", equipo.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Equipo> equipoList = equipoRepository.findAll();
        assertThat(equipoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
