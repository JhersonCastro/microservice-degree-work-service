package co.edu.unicauca.degreework.Comunication;

import co.edu.unicauca.degreework.DTO.ComunDTO;
import co.edu.unicauca.degreework.Model.DegreeWork;
import co.edu.unicauca.degreework.Service.DegreeWorkService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Consumer {

    @Autowired
    private DegreeWorkService degreeWorkService;

    @RabbitListener(queues = {"${comunQueue.name}"})
    public void receive(@Payload ComunDTO comunDTO) {
        System.out.println("=== Message received ===");
        try{
            DegreeWork degreeWork = actionStateComand(comunDTO.getId(), comunDTO.getAction());
            if(degreeWork != null){
                System.out.println("Recibido con éxito");
            }
        }catch(Exception e){
            System.out.println("ERROR");
        }
    }

    private void makeSlow() {
        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
    }

    private DegreeWork actionStateComand(Long dwId, String command) {

        return switch (command) {
            case "upload_format_a" -> degreeWorkService.uploadFormatA(dwId);
            case "reject_format_a" -> degreeWorkService.rejectFormatA(dwId);
            case "accept_format_a" -> degreeWorkService.acceptFormatA(dwId);
            case "upload_draft" -> degreeWorkService.uploadDraft(dwId);
            case "expire_draft", "reject_draft" -> degreeWorkService.expireDraftTime(dwId);
            case "aprove_draft" -> degreeWorkService.aproveDraft(dwId);
            default -> null;
        };

    }
}
