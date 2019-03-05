//package growfast;
//
//import lombok.extern.slf4j.Slf4j;
//
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//@Slf4j
//class LoadDatabase
//{
//    @Bean
//    CommandLineRunner initDatabase(GrowboxRepository repository)
//    {
//        return args ->
//            {
//                log.info("Dropping the DB");
//                repository.deleteAll();
//
//                log.info("Preloading " + repository.save(new Growbox("Box A")));
//                log.info("Preloading " + repository.save(new Growbox("Box B")));
//                log.info("Preloading " + repository.save(new Growbox("Box C")));
//                log.info("Preloading " + repository.save(new Growbox("Box D")));
//            };
//    }
//}
