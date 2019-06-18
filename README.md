# QueryDSL Predicate Builder


Quick setup

1. Add Maven dependencies 
    ```
    <dependency>
        <groupId>ro.andreimatei</groupId>
        <artifactId>querydsl-predicate-builder</artifactId>
        <version>1.0</version>
    </dependency>
    <dependency>
         <groupId>com.querydsl</groupId>
         <artifactId>querydsl-jpa</artifactId>
         <version>4.2.1</version>
    </dependency>
    <dependency>  
        <groupId>com.querydsl</groupId>
        <artifactId>querydsl-apt</artifactId>
        <version>4.2.1</version>
        <scope>provided</scope>
    </dependency>
    ```
2. Add Maven build plugin (JPA/Mongodb)
    
    2.1 JPA 
    ```
    <plugin>
        <groupId>com.mysema.maven</groupId>
        <artifactId>apt-maven-plugin</artifactId>
        <version>1.1.3</version>
        <executions>
            <execution>
                <goals>
                    <goal>process</goal>
                </goals>
                <configuration>
                    <outputDirectory>target/generated-sources/java</outputDirectory>
                    <processor>com.querydsl.apt.jpa.JPAAnnotationProcessor</processor>
                </configuration>
            </execution>
        </executions>
    </plugin>
    ```
    2.2 MongoDB
    ```asp
    <plugin>
        <groupId>com.mysema.maven</groupId>
        <artifactId>apt-maven-plugin</artifactId>
        <version>1.1.3</version>
        <executions>
            <execution>
                <goals>
                    <goal>process</goal>
                </goals>
                <configuration>
                    <outputDirectory>target/generated-sources/java</outputDirectory>
                    <processor>
                        org.springframework.data.mongodb.repository.support.MongoAnnotationProcessor
                    </processor>
                </configuration>
            </execution>
        </executions>
    </plugin>
    ```

3. Annotate each entity with `@Config`. E.g.:
    ```
    @Config(defaultVariableName = "ClassName")
    ```

4. Implement `QuerydslPredicateExecutor` for each Spring Repository. E.g:
    ```
    public interface ExampleRepository extends JpaRepository<ClassName, Long>, QuerydslPredicateExecutor<ClassName> { }
    ```

5. Run Maven build. This will generate all `QClasses` under `target/generated-sources/java`

6. Example usage
    ```
    QueryDSLPredicatesBuilder queryDSLPredicatesBuilder = new QueryDSLPredicatesBuilder(ClassName.class);
    ClassNameRepository.findAll(queryDSLPredicatesBuilder.with(filter).build(), pageable);
    ```
    
 Notes:
 
 If you intend to use a class that inherits from an external dependency:
 Create `package-info.java` at module level with the following structure:
```
 @QueryEntities({com.externalClass.Resource1.class, com.externalClass.Resource2.class})
 package com.package.model;
 
 import com.querydsl.core.annotations.QueryEntities;
```
