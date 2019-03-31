package com.irvin.markov;

import com.irvin.markov.entity.Message;
import com.irvin.markov.service.TextUtilityService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootTest(classes = MarkovApplication.class)
public class TextUtilityServiceTest {

    @Autowired
    TextUtilityService textUtilityService;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testGetMarkovString_success() throws IOException, URISyntaxException {

        int prefix = 3;
        int suffix = 2;
        int outputSize = 30;

        Path path = Paths.get(getClass().getClassLoader().getResource("testData/test.txt").toURI());
        Stream<String> lines = Files.lines(path);
        String fileContent = lines.collect(Collectors.joining(" "));
        lines.close();

        Message message = textUtilityService.getMarkovString(fileContent, prefix, suffix, outputSize);

        Assert.assertEquals(true, message.isSuccess());
    }

    @Test
    public void testGetMarkovString_outputSizeParamterIsTooBig() throws IOException, URISyntaxException {

        int prefix = 3;
        int suffix = 2;
        int outputSize = 1000;

        Path path = Paths.get(getClass().getClassLoader().getResource("testData/test.txt").toURI());
        Stream<String> lines = Files.lines(path);
        String fileContent = lines.collect(Collectors.joining(" "));
        lines.close();

        Message message = textUtilityService.getMarkovString(fileContent, prefix, suffix, outputSize);

        Assert.assertEquals(false, message.isSuccess());
        Assert.assertEquals("Output Size >= Word Count", message.getMessage());
    }

    @Test
    public void testGetMarkovString_prefixLengthIsGreaterThanOutputSize() throws IOException, URISyntaxException {

        int prefix = 31;
        int suffix = 2;
        int outputSize = 30;

        Path path = Paths.get(getClass().getClassLoader().getResource("testData/test.txt").toURI());
        Stream<String> lines = Files.lines(path);
        String fileContent = lines.collect(Collectors.joining(" "));
        lines.close();

        Message message = textUtilityService.getMarkovString(fileContent, prefix, suffix, outputSize);

        Assert.assertEquals(false, message.isSuccess());
        Assert.assertEquals("Output Size > Prefix Length", message.getMessage());
    }

    @Test
    public void testGetMarkovString_inputDataIsEmpty() throws IOException, URISyntaxException {

        int prefix = 31;
        int suffix = 2;
        int outputSize = 30;

        String fileContent = " ";

        Message message = textUtilityService.getMarkovString(fileContent, prefix, suffix, outputSize);

        Assert.assertEquals(false, message.isSuccess());
        Assert.assertEquals("Input data is empty", message.getMessage());
    }

}
