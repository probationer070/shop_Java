package 	com.demo.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.demo.domain.Item.Item;
import com.demo.domain.Item.ItemRepo;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ItemService {

	@Autowired
	private ItemRepo itemRepo;
	
	public void saveItem(Item item, MultipartFile imgFile) throws Exception {
		String oriImgName = imgFile.getOriginalFilename();
		String imgName = "";
		
		String projectPath = System.getProperty("user.dir") + "/src/main/resources/static/images/";
		
		UUID uuid = UUID.randomUUID();
		
		String savedFileName = uuid + "_" + oriImgName; // 파일명 -> imgName

        imgName = savedFileName;

        File saveFile = new File(projectPath, imgName);

        imgFile.transferTo(saveFile);

        item.setImgName(imgName);
        item.setImgPath("images/" + imgName);
		
		itemRepo.save(item);
	}

	public List<Item> TotalitemView() {
		return itemRepo.findAll();
	}

	public Item itemView(int id) {
		return itemRepo.findById((long) id).get();
	}
	
	@Transactional
	public void itemDelete(Integer id) {
		itemRepo.deleteById(id);
	}
}
