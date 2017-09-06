/*
package com.yang.springboot.batch;

import com.yang.springboot.cache.model.People;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;


*/
/**
 * @author Yangjing
 *//*

//@Configuration
//使用＠EnableBatchProcessing 开启对批处理的支持
@EnableBatchProcessing
public class CsvBatchConfig {

    @Bean
    public ItemReader<People> reader() {
        //使用FlatFileItemReader 读取文件。
        FlatFileItemReader<People> reader = new FlatFileItemReader<People>();

        //使用FlatFileItemReader 的setResource 方法设置csv 文件的路径。
        reader.setResource(new ClassPathResource("people.csv"));

        //在此处对CVS 文件的数据和领域模型类做对应映射。
        reader.setLineMapper(new DefaultLineMapper<People>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames(new String[]{"name", "age", "nation", "address"});
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<People>() {{
                setTargetType(People.class);
            }});
        }});
        return reader;
    }

    @Bean
    public CsvBeanValidator<People> csvBeanValidator() {
        return new CsvBeanValidator<People>();
    }

    @Bean
    public ItemProcessor<People, People> processor() {
        //使用我们自己定义的ItemProcessor 的实现CsvItemProcessor
        CsvItemProcessor processor = new CsvItemProcessor();

        //为processor 指定校验器为CsvBeanValidator;
        processor.setValidator(csvBeanValidator());
        return processor;
    }

    @Bean
    //Spring 能让容器中已有的Bean 以参数的形式注入， Spring Boot 己为我们定义了dataSource
    public ItemWriter<People> writer(DataSource dataSource) {
        //我们使用JDBC j比处理的JdbcBatchItemWriter 来写数据到数据库。
        JdbcBatchItemWriter<People> writer = new JdbcBatchItemWriter<People>();
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<People>());
        String sql = "insert into people " + "(id,name,age,nation,address) " +
                "values(hibernate_sequence.nextval, :name, :age, :nation, :address)";
        writer.setSql(sql);
        writer.setDataSource(dataSource);
        return writer;
    }

    @Bean
    public JobRepository jobRepository(DataSource dataSource, PlatformTransactionManager transactionManager) throws Exception {
        JobRepositoryFactoryBean jobRepositoryFactoryBean = new JobRepositoryFactoryBean();
        jobRepositoryFactoryBean.setDataSource(dataSource);
        jobRepositoryFactoryBean.setTransactionManager(transactionManager);
        jobRepositoryFactoryBean.setDatabaseType("oracle");
        return jobRepositoryFactoryBean.getObject();
    }

    @Bean
    public SimpleJobLauncher jobLauncher(DataSource dataSource, PlatformTransactionManager transactionManager) throws Exception {
        SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
        jobLauncher.setJobRepository(jobRepository(dataSource, transactionManager));
        return jobLauncher;
    }

    @Bean
    public CsvJobListener csvJobListener() {
        return new CsvJobListener();
    }

    @Bean
    public Job importJob(JobBuilderFactory jobBuilderFactory, Step s1) {
        return jobBuilderFactory.get("importJob")
                .incrementer(new RunIdIncrementer())
                .flow(s1)
                .end()
                .listener(csvJobListener())
                .build();
    }

    @Bean
    public Step step1(StepBuilderFactory stepBuilderFactory,
                      ItemReader<People> reader, ItemWriter<People> writer, ItemProcessor<People, People> processor) {
        return stepBuilderFactory.get("step1")
                //批处理每次捉交65000 条数据
                .<People, People>chunk(65000)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }
}
*/
