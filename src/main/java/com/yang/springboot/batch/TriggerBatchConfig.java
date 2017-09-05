package com.yang.springboot.batch;

import com.yang.springboot.model.People;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

/**
 * @author Yangjing
 */
@Configuration
//使用＠EnableBatchProcessing 开启对批处理的支持
@EnableBatchProcessing
public class TriggerBatchConfig {

    @Bean
    @StepScope
    public FlatFileItemReader<People> reader(@Value("#{jobParameters['input.file.name']}") String pathToFile) throws Exception {
        //使用FlatFileItemReader 读取文件。
        //平面文件是指包括最多二维（表格）数据的各种文件。利用FlatFileItemReader 类可以方便地在Spring Batch框架下读取平面文件，
        //该类提供了基本的平面文件读取和解析功能。FlatFileItemReader需要的两个最重的附属类是Resource和LineMapper
        FlatFileItemReader<People> reader = new FlatFileItemReader<People>();

        //使用FlatFileItemReader 的setResource 方法设置csv 文件的路径。
        reader.setResource(new ClassPathResource(pathToFile));

        //在此处对CVS 文件的数据和领域模型类做对应映射。
        reader.setLineMapper(new DefaultLineMapper<People>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames(new String[]{"name","age","nation","address"});
                setStrict(false);
            }});
            //只要 line mapper 将一行数据解析为单独的字段, 就会构建一个 FieldSet（包含命名好的字段), 然后将这个 FieldSet 对象传递给 mapFieldSet() 方法。 该方法负责创建对象来表示 CSV文件中的一行
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
        String sql = "insert into people(id,name,age,nation,address) values(hibernate_sequence.nextval, :name, :age, :nation, :address)";
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
