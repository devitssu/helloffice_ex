package com.kh.helloffice.reservation.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kh.helloffice.member.entity.DeptEmp;
import com.kh.helloffice.reservation.entity.AssetDto;
import com.kh.helloffice.reservation.entity.ReservationDto;
import com.kh.helloffice.reservation.entity.ReservManagerDto;
import com.kh.helloffice.reservation.service.ReservationManagementService;

@Controller
@RequestMapping("reserv-manage/{type}")
@Slf4j
public class ReservationManagementController {
	
	private final ReservationManagementService service;
	
	@Autowired
	public ReservationManagementController(ReservationManagementService service) {
		this.service = service;
	}

	@GetMapping()
	public String manage(@PathVariable String type, 
						 HttpSession session,
						 HttpServletResponse response) throws IOException {
		
		DeptEmp user = (DeptEmp)session.getAttribute("loginEmp");
		int adminLevel = user.getAdminLevel();
		if(adminLevel == 3) return "reservation/management";
		else {			
			long empNo = user.getEmpNo();
			
			try {
				if(checkPermission(type, empNo, 1)) {	
					return "reservation/management";
				}else {
					alert(response);
					return "reservation/management";
				}
			} catch (Exception e) {
				alert(response);
				return "reservation/management";
			}
		}
		
	}
	
	@GetMapping("asset")
	@ResponseBody
	public Map<Long, AssetDto> assetList(@PathVariable String type,
										 HttpSession session,
										 HttpServletResponse response) throws Exception {
		DeptEmp user = (DeptEmp)session.getAttribute("loginEmp");
		long empNo = user.getEmpNo();
		if(checkPermission(type, empNo, 2)) {			
			List<AssetDto> assetList = service.getAssetList(type);
			Map<Long, AssetDto> map = new HashMap<>();
			for (AssetDto a : assetList) {
				map.put(a.getAssetNo(), a);
			}
			return map;
		}else {
			alert(response);
			return null;
		}
	}
	
	
	@GetMapping("reserv")
	@ResponseBody
	public Map<Long, ReservationDto> reservList(@PathVariable String type) throws Exception {
		List<ReservationDto> reserveList = service.getReserveList(type);
		Map<Long, ReservationDto> reserveMap = new HashMap<>();
		for (ReservationDto r : reserveList) {
			reserveMap.put(r.getReservNo(), r);
		}
		return reserveMap;
	}
	
	@PutMapping("/{no}")
	@ResponseBody
	public String updateAsset(@PathVariable long no, 
							  @RequestBody AssetDto asset) throws Exception{
		asset.setAssetNo(no);
		int result = service.updateAsset(asset);
		if(result > 0) return "ok";
		else return "fail";
	}
	
	@DeleteMapping("/{no}")
	@ResponseBody
	public String deleteAsset(@PathVariable long no) throws Exception {
		int result = service.deleteAsset(no);
		if(result > 0) return "ok";
		else return "fail";
	}
	
	@PostMapping()
	@ResponseBody
	public String addAsset(@PathVariable String type, AssetDto asset) throws Exception {
		asset.setAssetType(type);
		int result = service.addAsset(asset);
		if(result > 0) return "success";
		else return "fail";
	}
	
	@GetMapping("/{no}")
	@ResponseBody
	public AssetDto getAsset( @PathVariable long no) throws Exception {
		AssetDto asset = service.getAsset(no);
		return asset;
	}
	
	@PutMapping("reserv/{reservNo}")
	@ResponseBody
	public String updateStatus(@PathVariable long reservNo,
						 	   @RequestBody ReservationDto reservation) throws Exception{

		reservation.setReservNo(reservNo);
		int result = service.updateStatus(reservation);
		if(result > 0) return "ok";
		else return "fail";
	}
	
	@GetMapping("manager")
	@ResponseBody
	public Map<Long, ReservManagerDto> getManagerList(@PathVariable String type,
													  HttpSession session,
													  HttpServletResponse response) throws Exception{
		DeptEmp user = (DeptEmp)session.getAttribute("loginEmp");
		long empNo = user.getEmpNo();
		if(checkPermission(type, empNo, 3)) {			
			List<ReservManagerDto> list = service.getManagerList(type);
			Map<Long, ReservManagerDto> map = new HashMap<>();
			for (ReservManagerDto m : list) {
				map.put(m.getEmpNo(), m);
			}
			return map;
		}else {
			alert(response);
			return null;
		}
	}
	
	@PostMapping("manager")
	@ResponseBody
	public String addManager(@PathVariable String type, 
							 @RequestBody Map<String, Object> map) throws Exception{
		map.put("type", type);
		int result = service.addManager(map);
		if(result > 0) return "ok";
		else return "ok";
	}
	
	@PutMapping("manager/{no}")
	@ResponseBody
	public String updateManager(@PathVariable String type,
								@PathVariable long no,
								@RequestBody String level) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("empNo", no);
		map.put("type", type);
		map.put("level", level);
		int result = service.updateManager(map);
		if(result > 0) return "ok";
		else return "fail";
	}
	
	@DeleteMapping("manager/{no}")
	@ResponseBody
	public String deleteManager(@PathVariable String type,
								@PathVariable long no) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("empNo", no);
		map.put("type", type);
		int result = service.deleteManager(map);
		if(result > 0) return "ok";
		else return "fail";
	}
	
	private boolean checkPermission(String type, long empNo, int level) throws Exception {
		Map<String, Object> map = new HashMap<>();
		map.put("empNo", empNo);
		map.put("type", type);
		int userLevel = service.getManagerLevel(map);
		if(userLevel == 0 || userLevel < level) {
			return false;
		}
		return true;
	}
	
	private void alert(HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		out.println("<script>alert('????????? ????????????.'); history.back();</script>");
		out.flush();
	}
	
}
