package pl.sda;

import pl.sda.entity.Option;
import pl.sda.entity.Question;
import pl.sda.entity.Quiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.sda.springquiz.controller.QuizController;
import pl.sda.springquiz.repository.SpringQuestionRepository;
import pl.sda.springquiz.repository.SpringQuizRepository;
import pl.sda.springquiz.service.NotEnoughPoints;
import pl.sda.springquiz.service.SpringQuizService;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

@SpringBootApplication
public class SpringQuizApp implements CommandLineRunner {
    final SpringQuizService quizService;
    final SpringQuizRepository quizRepository;
    final SpringQuestionRepository questionRepository;

    @Autowired
    public SpringQuizApp(SpringQuizService quizService, SpringQuizRepository quizRepository, SpringQuestionRepository questionRepository) {
        this.quizService = quizService;
        this.quizRepository = quizRepository;
        this.questionRepository = questionRepository;
    }

    public static void main(String[] args) {

        SpringApplication.run(SpringQuizApp.class, args);
    }

    public static void initData(SpringQuestionRepository repository, SpringQuizRepository quizRepository) {
        Set<Question> questions = new HashSet<>();
        Question q = Question.builder()
                .body("Wybierz słowo kluczowe Javy")
                .options(Option.builder()
                        .option1("Char")
                        .option2("integer")
                        .option3("boolean")
                        .option4("real")
                        .build())
                .validOption(3)
                .points(5)
                .build();
        questions.add(q);
        repository.save(q);
        q = Question.builder()
                .body("Wskaż instrukcję przerywającą iteracje.")
                .options(Option.builder()
                        .option1("switch")
                        .option2("return")
                        .option3("continue")
                        .option4("break")
                        .build())
                .validOption(4)
                .points(5)
                .build();
        questions.add(q);
        repository.save(q);
        q = Question.builder()
                .body("Które wyrażenie jest fałszem")
                .options(Option.builder()
                        .option1("10 > 5 && true")
                        .option2("\"a\".equals(\"a\")")
                        .option3("true == false")
                        .option4("10 != 4")
                        .build())
                .validOption(3)
                .points(5)
                .build();
        questions.add(q);
        repository.save(q);
        Quiz quiz = Quiz.builder().title("Język Java").questions(questions).build();
        quizRepository.save(quiz);
    }

    @Override
    public void run(String... args) throws Exception {
        try {
            initData(questionRepository, quizRepository);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        QuizController controller = new QuizController(quizService, 1);
        Scanner scanner = new Scanner(System.in);
        while(true){
            Question question = controller.next();
            System.out.println(question.getBody());
            System.out.println("1. " + question.getOptions().getOption1());
            System.out.println("2. " + question.getOptions().getOption2());
            System.out.println("3. " + question.getOptions().getOption3());
            System.out.println("4. " + question.getOptions().getOption4());
            System.out.println("0. Cofnij się do poprzedniego pytania");
            System.out.println("5. Koniec");
            int answer = scanner.nextInt();
            if (answer == 0){
                controller.previous();
                controller.previous();
                continue;
            }
            if (answer == 5){
                break;
            }
            controller.saveAnswer(question, answer);
        }
        controller.completeQuiz();
        System.out.println("Podsumowanie quizu: " + controller.summary());
        System.out.println("Transfer punktów");
        try {
            quizService.transferPoints(1L, 2L, 7);
            System.out.println("Punkty przetransferowane!");
        } catch (NotEnoughPoints e){
            System.out.println("Punktów nie można przesłać. Wystapił wyjątek: " + e.getMessage());
        }
        System.exit(0);
    }
}
