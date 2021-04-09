package ro.tacheandrei.diseratie.gestionarestudenti.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ro.tacheandrei.disertatie.components.dto.NomenclatorRequestDTO;
import ro.tacheandrei.disertatie.components.dto.PageDTO;
import ro.tacheandrei.disertatie.components.table.PageRequestDTO;
import ro.tacheandrei.disertatie.components.table.TableListDTO;

import java.util.List;
import java.util.Map;

@FeignClient(name = "administrare-service")
public interface NomenclatorFeign {

    @PostMapping("/api/nomenclatoare")
    List<Map<String, Object>> getNomenclatorByCod(@RequestBody NomenclatorRequestDTO nomenclatorRequestDTO);

    @PostMapping("/api/nomenclatoare/{proiect}/{cod}")
    TableListDTO<PageDTO> getGridNomenclatoareByCod(@PathVariable String proiect, @PathVariable  String cod, @RequestBody PageRequestDTO pageRequestDTO);
}
