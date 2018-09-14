package com.xedflix.video.web.rest;

import com.xedflix.video.XedflixVideoServiceApp;

import com.xedflix.video.domain.Livestream;
import com.xedflix.video.repository.LivestreamRepository;
import com.xedflix.video.service.LivestreamService;
import com.xedflix.video.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;


import static com.xedflix.video.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the LivestreamResource REST controller.
 *
 * @see LivestreamResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = XedflixVideoServiceApp.class)
@ActiveProfiles("test")
public class LivestreamResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_STREAM_KEY = "AAAAAAAAAA";
    private static final String UPDATED_STREAM_KEY = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_SCHEDULED = false;
    private static final Boolean UPDATED_IS_SCHEDULED = true;

    private static final LocalDate DEFAULT_SCHEDULED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_SCHEDULED_AT = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_IMAGE_URL = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_URL = "BBBBBBBBBB";

    private static final String DEFAULT_RECORDED_FILE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_RECORDED_FILE_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_STARTED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_STARTED_AT = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_ENDED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ENDED_AT = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_HAS_STARTED = false;
    private static final Boolean UPDATED_HAS_STARTED = true;

    private static final Boolean DEFAULT_HAS_ENDED = false;
    private static final Boolean UPDATED_HAS_ENDED = true;

    private static final Long DEFAULT_USER_ID = 1L;
    private static final Long UPDATED_USER_ID = 2L;

    private static final Long DEFAULT_ORGANIZATION_ID = 1L;
    private static final Long UPDATED_ORGANIZATION_ID = 2L;

    private static final LocalDate DEFAULT_CREATED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_AT = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_UPDATED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATED_AT = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_IS_ARCHIVED = false;
    private static final Boolean UPDATED_IS_ARCHIVED = true;

    @Autowired
    private LivestreamRepository livestreamRepository;

    

    @Autowired
    private LivestreamService livestreamService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restLivestreamMockMvc;

    private Livestream livestream;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LivestreamResource livestreamResource = new LivestreamResource(livestreamService);
        this.restLivestreamMockMvc = MockMvcBuilders.standaloneSetup(livestreamResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Livestream createEntity(EntityManager em) {
        Livestream livestream = new Livestream()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .streamKey(DEFAULT_STREAM_KEY)
            .isScheduled(DEFAULT_IS_SCHEDULED)
            .scheduledAt(DEFAULT_SCHEDULED_AT)
            .imageUrl(DEFAULT_IMAGE_URL)
            .recordedFileName(DEFAULT_RECORDED_FILE_NAME)
            .startedAt(DEFAULT_STARTED_AT)
            .endedAt(DEFAULT_ENDED_AT)
            .hasStarted(DEFAULT_HAS_STARTED)
            .hasEnded(DEFAULT_HAS_ENDED)
            .userId(DEFAULT_USER_ID)
            .organizationId(DEFAULT_ORGANIZATION_ID)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT)
            .isArchived(DEFAULT_IS_ARCHIVED);
        return livestream;
    }

    @Before
    public void initTest() {
        livestream = createEntity(em);
    }

    @Test
    @Transactional
    public void createLivestream() throws Exception {
        int databaseSizeBeforeCreate = livestreamRepository.findAll().size();

        // Create the Livestream
        restLivestreamMockMvc.perform(post("/api/livestreams")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(livestream)))
            .andExpect(status().isCreated());

        // Validate the Livestream in the database
        List<Livestream> livestreamList = livestreamRepository.findAll();
        assertThat(livestreamList).hasSize(databaseSizeBeforeCreate + 1);
        Livestream testLivestream = livestreamList.get(livestreamList.size() - 1);
        assertThat(testLivestream.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testLivestream.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testLivestream.getStreamKey()).isEqualTo(DEFAULT_STREAM_KEY);
        assertThat(testLivestream.isIsScheduled()).isEqualTo(DEFAULT_IS_SCHEDULED);
        assertThat(testLivestream.getScheduledAt()).isEqualTo(DEFAULT_SCHEDULED_AT);
        assertThat(testLivestream.getImageUrl()).isEqualTo(DEFAULT_IMAGE_URL);
        assertThat(testLivestream.getRecordedFileName()).isEqualTo(DEFAULT_RECORDED_FILE_NAME);
        assertThat(testLivestream.getStartedAt()).isEqualTo(DEFAULT_STARTED_AT);
        assertThat(testLivestream.getEndedAt()).isEqualTo(DEFAULT_ENDED_AT);
        assertThat(testLivestream.isHasStarted()).isEqualTo(DEFAULT_HAS_STARTED);
        assertThat(testLivestream.isHasEnded()).isEqualTo(DEFAULT_HAS_ENDED);
        assertThat(testLivestream.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testLivestream.getOrganizationId()).isEqualTo(DEFAULT_ORGANIZATION_ID);
        assertThat(testLivestream.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testLivestream.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testLivestream.isIsArchived()).isEqualTo(DEFAULT_IS_ARCHIVED);
    }

    @Test
    @Transactional
    public void createLivestreamWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = livestreamRepository.findAll().size();

        // Create the Livestream with an existing ID
        livestream.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLivestreamMockMvc.perform(post("/api/livestreams")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(livestream)))
            .andExpect(status().isBadRequest());

        // Validate the Livestream in the database
        List<Livestream> livestreamList = livestreamRepository.findAll();
        assertThat(livestreamList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllLivestreams() throws Exception {
        // Initialize the database
        livestreamRepository.saveAndFlush(livestream);

        // Get all the livestreamList
        restLivestreamMockMvc.perform(get("/api/livestreams?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(livestream.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].streamKey").value(hasItem(DEFAULT_STREAM_KEY.toString())))
            .andExpect(jsonPath("$.[*].isScheduled").value(hasItem(DEFAULT_IS_SCHEDULED.booleanValue())))
            .andExpect(jsonPath("$.[*].scheduledAt").value(hasItem(DEFAULT_SCHEDULED_AT.toString())))
            .andExpect(jsonPath("$.[*].imageUrl").value(hasItem(DEFAULT_IMAGE_URL.toString())))
            .andExpect(jsonPath("$.[*].recordedFileName").value(hasItem(DEFAULT_RECORDED_FILE_NAME.toString())))
            .andExpect(jsonPath("$.[*].startedAt").value(hasItem(DEFAULT_STARTED_AT.toString())))
            .andExpect(jsonPath("$.[*].endedAt").value(hasItem(DEFAULT_ENDED_AT.toString())))
            .andExpect(jsonPath("$.[*].hasStarted").value(hasItem(DEFAULT_HAS_STARTED.booleanValue())))
            .andExpect(jsonPath("$.[*].hasEnded").value(hasItem(DEFAULT_HAS_ENDED.booleanValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].organizationId").value(hasItem(DEFAULT_ORGANIZATION_ID.intValue())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].isArchived").value(hasItem(DEFAULT_IS_ARCHIVED.booleanValue())));
    }
    

    @Test
    @Transactional
    public void getLivestream() throws Exception {
        // Initialize the database
        livestreamRepository.saveAndFlush(livestream);

        // Get the livestream
        restLivestreamMockMvc.perform(get("/api/livestreams/{id}", livestream.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(livestream.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.streamKey").value(DEFAULT_STREAM_KEY.toString()))
            .andExpect(jsonPath("$.isScheduled").value(DEFAULT_IS_SCHEDULED.booleanValue()))
            .andExpect(jsonPath("$.scheduledAt").value(DEFAULT_SCHEDULED_AT.toString()))
            .andExpect(jsonPath("$.imageUrl").value(DEFAULT_IMAGE_URL.toString()))
            .andExpect(jsonPath("$.recordedFileName").value(DEFAULT_RECORDED_FILE_NAME.toString()))
            .andExpect(jsonPath("$.startedAt").value(DEFAULT_STARTED_AT.toString()))
            .andExpect(jsonPath("$.endedAt").value(DEFAULT_ENDED_AT.toString()))
            .andExpect(jsonPath("$.hasStarted").value(DEFAULT_HAS_STARTED.booleanValue()))
            .andExpect(jsonPath("$.hasEnded").value(DEFAULT_HAS_ENDED.booleanValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID.intValue()))
            .andExpect(jsonPath("$.organizationId").value(DEFAULT_ORGANIZATION_ID.intValue()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()))
            .andExpect(jsonPath("$.isArchived").value(DEFAULT_IS_ARCHIVED.booleanValue()));
    }
    @Test
    @Transactional
    public void getNonExistingLivestream() throws Exception {
        // Get the livestream
        restLivestreamMockMvc.perform(get("/api/livestreams/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLivestream() throws Exception {
        // Initialize the database
        livestreamService.save(livestream);

        int databaseSizeBeforeUpdate = livestreamRepository.findAll().size();

        // Update the livestream
        Livestream updatedLivestream = livestreamRepository.findById(livestream.getId()).get();
        // Disconnect from session so that the updates on updatedLivestream are not directly saved in db
        em.detach(updatedLivestream);
        updatedLivestream
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .streamKey(UPDATED_STREAM_KEY)
            .isScheduled(UPDATED_IS_SCHEDULED)
            .scheduledAt(UPDATED_SCHEDULED_AT)
            .imageUrl(UPDATED_IMAGE_URL)
            .recordedFileName(UPDATED_RECORDED_FILE_NAME)
            .startedAt(UPDATED_STARTED_AT)
            .endedAt(UPDATED_ENDED_AT)
            .hasStarted(UPDATED_HAS_STARTED)
            .hasEnded(UPDATED_HAS_ENDED)
            .userId(UPDATED_USER_ID)
            .organizationId(UPDATED_ORGANIZATION_ID)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .isArchived(UPDATED_IS_ARCHIVED);

        restLivestreamMockMvc.perform(put("/api/livestreams")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedLivestream)))
            .andExpect(status().isOk());

        // Validate the Livestream in the database
        List<Livestream> livestreamList = livestreamRepository.findAll();
        assertThat(livestreamList).hasSize(databaseSizeBeforeUpdate);
        Livestream testLivestream = livestreamList.get(livestreamList.size() - 1);
        assertThat(testLivestream.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testLivestream.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testLivestream.getStreamKey()).isEqualTo(UPDATED_STREAM_KEY);
        assertThat(testLivestream.isIsScheduled()).isEqualTo(UPDATED_IS_SCHEDULED);
        assertThat(testLivestream.getScheduledAt()).isEqualTo(UPDATED_SCHEDULED_AT);
        assertThat(testLivestream.getImageUrl()).isEqualTo(UPDATED_IMAGE_URL);
        assertThat(testLivestream.getRecordedFileName()).isEqualTo(UPDATED_RECORDED_FILE_NAME);
        assertThat(testLivestream.getStartedAt()).isEqualTo(UPDATED_STARTED_AT);
        assertThat(testLivestream.getEndedAt()).isEqualTo(UPDATED_ENDED_AT);
        assertThat(testLivestream.isHasStarted()).isEqualTo(UPDATED_HAS_STARTED);
        assertThat(testLivestream.isHasEnded()).isEqualTo(UPDATED_HAS_ENDED);
        assertThat(testLivestream.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testLivestream.getOrganizationId()).isEqualTo(UPDATED_ORGANIZATION_ID);
        assertThat(testLivestream.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testLivestream.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testLivestream.isIsArchived()).isEqualTo(UPDATED_IS_ARCHIVED);
    }

    @Test
    @Transactional
    public void updateNonExistingLivestream() throws Exception {
        int databaseSizeBeforeUpdate = livestreamRepository.findAll().size();

        // Create the Livestream

        // If the entity doesn't have an ID, it will throw BadRequestAlertException 
        restLivestreamMockMvc.perform(put("/api/livestreams")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(livestream)))
            .andExpect(status().isBadRequest());

        // Validate the Livestream in the database
        List<Livestream> livestreamList = livestreamRepository.findAll();
        assertThat(livestreamList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLivestream() throws Exception {
        // Initialize the database
        livestreamService.save(livestream);

        int databaseSizeBeforeDelete = livestreamRepository.findAll().size();

        // Get the livestream
        restLivestreamMockMvc.perform(delete("/api/livestreams/{id}", livestream.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Livestream> livestreamList = livestreamRepository.findAll();
        assertThat(livestreamList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Livestream.class);
        Livestream livestream1 = new Livestream();
        livestream1.setId(1L);
        Livestream livestream2 = new Livestream();
        livestream2.setId(livestream1.getId());
        assertThat(livestream1).isEqualTo(livestream2);
        livestream2.setId(2L);
        assertThat(livestream1).isNotEqualTo(livestream2);
        livestream1.setId(null);
        assertThat(livestream1).isNotEqualTo(livestream2);
    }
}
