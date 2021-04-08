package ro.tacheandrei.diseratie.gestionarestudenti.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ro.tacheandrei.disertatie.components.dto.NomenclatorRequestDTO;

import java.util.List;

@FeignClient(name = "administrare-service")
public interface NomenclatorFeign {

    @PostMapping("/api/nomenclatoare")
    List<Object> getNomenclatorByCod(@RequestBody NomenclatorRequestDTO nomenclatorRequestDTO);
}
