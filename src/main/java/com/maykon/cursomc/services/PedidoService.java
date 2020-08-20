package com.maykon.cursomc.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amazonaws.services.licensemanager.model.AuthorizationException;
import com.maykon.cursomc.domain.Cliente;
import com.maykon.cursomc.domain.ItemPedido;
import com.maykon.cursomc.domain.PagamentoComBoleto;
import com.maykon.cursomc.domain.Pedido;
import com.maykon.cursomc.domain.enums.EstadoPagamento;
import com.maykon.cursomc.repositories.ItemPedidoRepository;
import com.maykon.cursomc.repositories.PagamentoRepository;
import com.maykon.cursomc.repositories.PedidoRepository;
import com.maykon.cursomc.security.UserSS;
import com.maykon.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository repo;

	@Autowired
	private ProdutoService produtoService;
	
	//@Autowired
	//private ProdutoRepository repoProd;

	@Autowired
	private ItemPedidoRepository repoip;
	
	@Autowired
	private BoletoService boletoService;
	
	@Autowired
	private PagamentoRepository repopag;
	
	@Autowired
	private ClienteService clienteService;
	
	//@Autowired
	//private ClienteRepository clienteRepo;	
	
	//@Autowired
	//private EmailService emailService;
	
	@Autowired
	private SmtpEmailService smtpEmailService;
	
	
	public Pedido buscar(Integer id) {
		Optional<Pedido> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(	
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
	}
	
	@Transactional
	public Pedido insert(Pedido obj) {
		obj.setId(null);
		obj.setInstante(new Date());
		obj.setCliente(clienteService.buscar(obj.getCliente().getId()));
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);

		if (obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagto,obj.getInstante());
		}
		obj = repo.save(obj);
		repopag.save(obj.getPagamento());
		 
		for (ItemPedido ip : obj.getItens()) {
			ip.setDesconto(0.0);
			ip.setProduto(produtoService.buscar(ip.getProduto().getId()));
			ip.setPreco(produtoService.buscar(ip.getProduto().getId()).getPreco());
			ip.setPedido(obj);
		}
		repoip.saveAll(obj.getItens());
		//System.out.println(obj);
		//emailService.envioPedidoConfirmado(obj);
		//smtpEmailService.envioPedidoConfirmado(obj);
		smtpEmailService.sendOrderConfirmationHtmlEmail(obj);
		return obj;
	}
	
	public Page<Pedido> findPage(Integer page,Integer linesPerPage,String orderBy,String direction){
		
		UserSS user = UserService.authenticated();
		
		if (user ==null) {
			throw new AuthorizationException("Acesso Negado");
		}
		
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction) ,orderBy);
		
		Cliente cliente = clienteService.buscar(user.getId());
		System.out.println(cliente);
		return repo.findByCliente(cliente, pageRequest);
	}

}
