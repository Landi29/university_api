package com.example.university_api;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
class UniversityApiApplicationTests {

	public String mapToJson(Object obj) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(obj);
	}

	public <T> T mapFromJson(String json, Class<T> outputclass)
			throws JsonParseException, JsonMappingException, IOException {

		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.readValue(json, outputclass);
	}

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ProfessorController controller;

	@Test
	void contextLoads() throws Exception {
		assertThat(controller).isNotNull();

	}

	@Test
	public void getProfessorsTest() throws Exception{
		MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.get("/professors").accept(
				MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
		String content = result.getResponse().getContentAsString();
		Professor [] professors = mapFromJson(content, Professor[].class);
		assertThat(professors.length).isGreaterThan(0);
	}

	@Test
	public void addProfessor() throws Exception{
		Professor professorToAdd = new Professor("Bin Xuan", "Database", "Distributed Systems",
				"bxuan@universitet.dk");
		List<String> courses = new ArrayList<>(Arrays.asList("Distributed Systems", "Operating Systems"));
		professorToAdd.setCourses(courses);
		String professorAsJson = mapToJson(professorToAdd);

		MvcResult result = mockMvc.perform(post("/professors").
				contentType(MediaType.APPLICATION_JSON).content(professorAsJson)).andExpect(status().isCreated()).andReturn();

		String content = result.getResponse().getContentAsString();
		Professor returned_value = mapFromJson(content, Professor.class);
		// Set the id of initial professor since this is a generated value.
		professorToAdd.setId(returned_value.getId());
		assertThat(professorToAdd).isEqualTo(returned_value);
	}

	@Test
	public void updateProfessor() throws Exception{
		Professor professor = new Professor("Hans Jensen", "Database",
				"Big Data and Recommender Systems", "hjsen@universitet.dk");
		String professorAsJson = mapToJson(professor);
		MvcResult result = mockMvc.perform(put("/professors/1").contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(professorAsJson)).andExpect(status().isOk()).andReturn();

		String content = result.getResponse().getContentAsString();
		Professor returned_value = mapFromJson(content, Professor.class);
		// Set the id of initial professor.
		professor.setId(returned_value.getId());
		assertThat(professor).isEqualTo(returned_value);
	}

	@Test
	public void deleteProfessor() throws Exception{
		mockMvc.perform(delete("/professors/1")).andExpect(status().isOk());
	}

	
}
