package com.example.customvoca.view

/*
class VocabListFragment : Fragment() {
    private val dicListViewModel: DicListViewModel by activityViewModels()
    private lateinit var binding: FragmentDiclistBinding
    private lateinit var adapter: DicListAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_vocablist, container, false)
        binding.lifecycleOwner = this.viewLifecycleOwner
        binding.dicListViewModel = dicListViewModel

        adapter = DicListAdapter(dicListViewModel)
        binding.recyclerViewDiclist.adapter = adapter
        binding.recyclerViewDiclist.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewDiclist.itemAnimator = null

        binding.btnAdd.setOnClickListener{
            dicListViewModel.addDic(binding.textDicName.text.toString())
        }

        dicListViewModel.dicListItems.observe(viewLifecycleOwner) { items ->
            binding.swipeRefreshRecycler.isRefreshing = false
            adapter.updateItem(items)
            Log.d("DicListViewModel", "dicListItems Observed change" +  items)
        }

        binding.swipeRefreshRecycler.setOnRefreshListener {
            dicListViewModel.updateItems()
        }

        binding.btnAdmin.setOnClickListener{
            startActivity(Intent(requireContext(), AdminActivity::class.java))
        }
        return binding.root
    }
}
 */